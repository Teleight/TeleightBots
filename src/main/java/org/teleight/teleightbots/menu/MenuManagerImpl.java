package org.teleight.teleightbots.menu;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.api.methods.callback.CallbackQuery;
import org.teleight.teleightbots.api.methods.inline.EditMessageReplyMarkup;
import org.teleight.teleightbots.api.methods.inline.EditMessageText;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.chat.Chat;
import org.teleight.teleightbots.api.objects.keyboard.InlineKeyboardMarkup;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.event.EventManager;
import org.teleight.teleightbots.event.keyboard.ButtonPressEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class MenuManagerImpl implements MenuManager {

    private final Bot bot;
    private final EventManager eventManager;
    private final Map<Integer, Menu> menus = new ConcurrentHashMap<>();

    public MenuManagerImpl(Bot bot) {
        this.bot = bot;
        this.eventManager = bot.createNewEventNode();

        eventManager.addListener(ButtonPressEvent.class, event -> {
            final Collection<Menu> menus = getMenus();
            for (final Menu menu : menus) {
                handleMenu((MenuImpl) menu, event);
            }
        });
    }

    private void handleMenu(@NotNull MenuImpl menu, @NotNull ButtonPressEvent event) {
        final List<List<MenuButton>> internalColumns = menu.getColumns();
        for (final List<MenuButton> columns : internalColumns) {
            for (final MenuButton buttonInRow : columns) {
                handleButton(buttonInRow, event);
            }
        }
    }

    private void handleButton(@NotNull MenuButton rowButton, @NotNull ButtonPressEvent event) {
        final CallbackQuery callbackQuery = event.callbackQuery();
        final Message message = callbackQuery.message();

        if (message == null) {
            bot.getLogger().warn("Tried to handle button {} but no message was found", rowButton.callbackData());
            return;
        }

        final Chat chat = message.chat();
        final String chatId = "" + chat.id();
        final String callbackData = callbackQuery.data();
        final String inlineMessageId = callbackQuery.inlineMessageId();

        if (inlineMessageId == null) {
            bot.getLogger().warn("Tried to handle button {} but no inline message id was found", rowButton.callbackData());
            return;
        }

        final int messageId = message.messageId();

        if(callbackData == null){
            return;
        }

        final boolean hasClickedThisButton = callbackData.equals(rowButton.callbackData());
        if (!hasClickedThisButton) {
            return;
        }
        final boolean hasCallback = rowButton.callback() != null;
        final boolean hasDestinationMenu = rowButton.destinationMenu() != null;

        if(hasCallback) {
            rowButton.callback().accept(event, event.callbackQuery().from());
            return;
        }

        if(hasDestinationMenu) {
            final Menu destinationMenu = rowButton.destinationMenu();
            final InlineKeyboardMarkup keyboard = destinationMenu.getKeyboard();
            final String text = destinationMenu.getText();
            final boolean shouldChangeText = text != null;

            if (shouldChangeText) {
                final EditMessageText editMessageText = EditMessageText.builder()
                        .text(text)
                        .chatId(chatId)
                        .messageId(messageId)
                        .replyMarkup(keyboard)
                        .build();
                event.execute(editMessageText);
            } else {
                final EditMessageReplyMarkup editMessageReplyMarkup = EditMessageReplyMarkup.builder()
                        .chatId(chatId)
                        .messageId(messageId)
                        .inlineMessageId(inlineMessageId)
                        .replyMarkup(keyboard)
                        .build();
                event.execute(editMessageReplyMarkup);
            }
        }
    }

    @Override
    public @NotNull EventManager getEventNode() {
        return eventManager;
    }

    @Override
    public void registerMenu(@NotNull Menu menu) {
        if (menus.containsKey(menu.getMenuId())) {
            throw new IllegalArgumentException("The menu " + menu.getMenuName() + " has already been registered");
        }
        menus.put(menu.getMenuId(), menu);
    }

    @Override
    public @NotNull @Unmodifiable Collection<Menu> getMenus() {
        return Collections.unmodifiableCollection(menus.values());
    }

    @Override
    public @Nullable Menu getMenu(String name) {
        for (Menu value : menus.values()) {
            if(name.equals(value.getMenuName())){
                return value;
            }
        }
        return null;
    }

    @Override
    public void unregisterMenu(int menuId) {
        if (!menus.containsKey(menuId)) {
            throw new IllegalArgumentException("The menu with id " + menuId + " doesn't exist");
        }
        menus.remove(menuId);
    }

}
