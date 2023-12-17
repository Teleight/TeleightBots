package org.teleight.teleightbots.updateprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.MultiPartApiMethod;
import org.teleight.teleightbots.api.methods.GetUpdates;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.api.objects.chat.Chat;
import org.teleight.teleightbots.api.objects.chat.ChatMemberUpdated;
import org.teleight.teleightbots.api.objects.chat.member.*;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.bot.BotSettings;
import org.teleight.teleightbots.event.bot.UpdateReceivedEvent;
import org.teleight.teleightbots.event.bot.channel.BotJoinChannelEvent;
import org.teleight.teleightbots.event.bot.channel.BotQuitChannelEvent;
import org.teleight.teleightbots.event.bot.channel.ChannelSendMessageEvent;
import org.teleight.teleightbots.event.bot.group.BotJoinedGroupEvent;
import org.teleight.teleightbots.event.bot.group.BotLeftGroupEvent;
import org.teleight.teleightbots.event.keyboard.ButtonPressEvent;
import org.teleight.teleightbots.event.user.UserInlineQueryReceivedEvent;
import org.teleight.teleightbots.event.user.UserMessageReceivedEvent;
import org.teleight.teleightbots.exception.exceptions.RateLimitException;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;
import org.teleight.teleightbots.utils.MultiPartBodyPublisher;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class LongPollingUpdateProcessor implements UpdateProcessor {

    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.of(10, ChronoUnit.SECONDS))
            .version(HttpClient.Version.HTTP_2)
            .build();
    private Thread updateProcessorThread;
    private Bot bot;
    private int lastReceivedUpdate = 0;

    @Override
    public void setBot(@NotNull Bot bot) {
        if (this.bot != null) {
            throw new IllegalArgumentException("Bot instance was already assigned to this update processor");
        }
        this.bot = bot;
    }

    @Override
    public void start() {
        updateProcessorThread = new Thread(new UpdateProcessorThread());
        updateProcessorThread.setName(bot.getBotUsername() + " Update Processor");
        updateProcessorThread.start();
    }

    public void shutdown() {
        updateProcessorThread.interrupt();
    }

    private void executeGetUpdates() {
        final BotSettings settings = bot.getBotSettings();

        final GetUpdates getUpdates = GetUpdates.of()
                .withTimeout(settings.updatesTimeout())
                .withLimit(settings.updatesLimit())
                .withOffset(lastReceivedUpdate + 1);

        final String responseJson = UNSAFE_executeMethod(getUpdates)
                .exceptionally(throwable -> {
                    return null;
                })
                .join();
        if(responseJson == null){
            return;
        }

        Update[] updates;
        try {
            updates = getUpdates.deserializeResponse(responseJson);
        } catch (TelegramRequestException e) {
            TeleightBots.getExceptionManager().handleException(e);
            return;
        }

        if (updates.length == 0) {
            return;
        }

        // Mark updates for removal
        int newSize = 0;
        for (int i = 0; i < updates.length; i++) {
            Update update = updates[i];
            if (update.updateId() >= lastReceivedUpdate) {
                updates[newSize] = update;
                newSize++;
            }
        }

        // Compact the array
        if (newSize < updates.length) {
            Update[] newUpdates = new Update[newSize];
            System.arraycopy(updates, 0, newUpdates, 0, newSize);
            updates = newUpdates;
        }

        // Process the updates
        for (final Update update : updates) {
            handleNewUpdate(update, responseJson);
        }

        // Update lastReceivedUpdate
        if (updates.length > 0) {
            lastReceivedUpdate = updates[updates.length - 1].updateId();
        }
    }

    @ApiStatus.Internal
    private void handleNewUpdate(@NotNull Update update, String responseJson) {
        bot.getEventManager()
                .call(new UpdateReceivedEvent(bot, update, responseJson))
                .thenAccept(updateReceivedEvent -> {
                    final boolean hasCallbackQuery = update.callbackQuery() != null;
                    if (hasCallbackQuery) {
                        final AtomicBoolean completed = new AtomicBoolean();
                        final ButtonPressEvent buttonPressEvent = new ButtonPressEvent(bot, update, completed);
                        bot.getMenuManager().getEventNode().call(buttonPressEvent).thenAccept(event -> {
                            if (completed.get()) {
                                return;
                            }
                            event.completeCallback();
                        });
                    }
                    final boolean hasMessage = update.message() != null;
                    if (hasMessage) {
                        final Message message = update.message();
                        final User sender = message.from();
                        final String messageText = message.text();

                        final boolean hasText = messageText != null;
                        final boolean hasSender = sender != null;
                        final boolean hasFormat = message.forwardFrom() == null;

                        if (hasText && hasSender) {
                            final boolean isPossibleCommand = messageText.startsWith("/");
                            if (isPossibleCommand) {
                                bot.getCommandManager().execute(sender, messageText, message);
                            }
                        }


                        //Write
                        if (hasText && hasFormat) {
                            bot.getEventManager().call(new UserMessageReceivedEvent(bot, update));
                        }


                        //Bot join
                        final boolean hasNewChatMembers = message.newChatMembers() != null;
                        if (hasNewChatMembers) {
                            boolean isThisBotJoined = Arrays.stream(message.newChatMembers())
                                    .filter(Objects::nonNull)
                                    .filter(user -> user.username() != null)
                                    .anyMatch(user -> user.username().equalsIgnoreCase(bot.getBotUsername()));
                            Boolean groupChatCreated = message.groupChatCreated();
                            if (isThisBotJoined || (groupChatCreated != null && groupChatCreated)) {
                                bot.getEventManager().call(new BotJoinedGroupEvent(bot, update));
                            }
                        }
                    }


                    final ChatMemberUpdated myChatMember = update.myChatMember();
                    final boolean hasMyChatMember = myChatMember != null;
                    if (hasMyChatMember) {
                        final ChatMember newChatMember = myChatMember.newChatMember();
                        final boolean isThisBot = bot.getBotUsername().equals(newChatMember.user().username());
                        if (isThisBot) {
                            final boolean isJoined = newChatMember instanceof ChatMemberAdministrator || newChatMember instanceof ChatMemberMember;
                            final boolean isLeft = newChatMember instanceof ChatMemberLeft || newChatMember instanceof ChatMemberRestricted;

                            final Chat chat = myChatMember.chat();
                            final boolean isChannel = chat.isChannel();


                            if (isJoined) {
                                if (isChannel) {
                                    bot.getEventManager().call(new BotJoinChannelEvent(bot, update));
                                } else {
                                    bot.getEventManager().call(new BotJoinedGroupEvent(bot, update));
                                }
                            }

                            if (isLeft) {
                                if (isChannel) {
                                    bot.getEventManager().call(new BotQuitChannelEvent(bot, update));
                                } else {
                                    bot.getEventManager().call(new BotLeftGroupEvent(bot, update));
                                }
                            }
                        }
                    }


                    //Inline
                    if (update.inlineQuery() != null) {
                        bot.getEventManager().call(new UserInlineQueryReceivedEvent(bot, update));
                    }


                    final Message channelPost = update.channelPost();
                    final boolean hasChannelPost = channelPost != null;
                    if (hasChannelPost) {
                        final Chat chat = channelPost.chat();
                        final boolean isChannel = chat.isChannel();
                        if (isChannel) {
                            bot.getEventManager().call(new ChannelSendMessageEvent(bot, update, chat));
                        }
                    }

                    //Conversation
                    bot.getConversationManager().getRunningConversations().forEach(runningConversation -> {
                        runningConversation.getEventManager().call(updateReceivedEvent);
                    });
                });
    }

    @Override
    public @NotNull CompletableFuture<String> executeMethod(@NotNull ApiMethod<?> method) {
        return UNSAFE_executeMethod(method);
    }

    @ApiStatus.Internal
    public @NotNull CompletableFuture<String> UNSAFE_executeMethod(@NotNull ApiMethod<?> method) {
        //Request
        final String botToken = bot.getBotToken();
        final BotSettings settings = bot.getBotSettings();
        final String url = settings.endpointUrl() + botToken + "/" + method.getEndpointURL();

        final CompletableFuture<String> requestFuture = new CompletableFuture<>();

        Thread.ofVirtual().name(bot.getBotUsername() + " LongPolling Request Dispatcher").start(() -> {
            try {
                final HttpRequest request;
                try {
                    request = createRequest(method, url);
                } catch (JsonProcessingException e) {
                    requestFuture.completeExceptionally(e);
                    return;
                }
                final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                final int statusCode = response.statusCode();
                final String responseBody = response.body();

                if (statusCode == 429) {
                    //Rate limit reached
                    requestFuture.completeExceptionally(new RateLimitException());
                    return;
                }

                requestFuture.complete(responseBody);
            } catch (IOException e) {
                requestFuture.completeExceptionally(e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                requestFuture.completeExceptionally(e);
            }
        });
        return requestFuture;
    }

    @ApiStatus.Internal
    private HttpRequest createRequest(ApiMethod<?> method, String url) throws JsonProcessingException {
        final HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(URI.create(url));
        final HttpRequest.BodyPublisher body;

        if (method instanceof MultiPartApiMethod<?> multiPartApiMethod) {
            final MultiPartBodyPublisher publisher = new MultiPartBodyPublisher();
            multiPartApiMethod.buildRequest(publisher);
            body = publisher.build();

            requestBuilder.header("Content-Type", "multipart/form-data; boundary=" + publisher.getBoundary());
        } else {
            final String jsonString = ApiMethod.OBJECT_MAPPER.writeValueAsString(method);
            body = HttpRequest.BodyPublishers.ofString(jsonString);

            requestBuilder.header("Content-Type", "application/json");
        }

        return requestBuilder.POST(body).build();
    }

    private class UpdateProcessorThread implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                executeGetUpdates();
            }
        }
    }

}
