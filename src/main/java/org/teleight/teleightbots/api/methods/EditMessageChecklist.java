package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.InputChecklist;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record EditMessageChecklist(
        @JsonProperty(value = "business_connection_id", required = true)
        String businessConnectionId,

        @JsonProperty(value = "chat_id", required = true)
        String chatId,

        @JsonProperty(value = "message_id", required = true)
        int messageId,

        @JsonProperty(value = "checklist", required = true)
        InputChecklist checklist,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements ApiMethod<Message> {

    public static @NotNull Builder ofBuilder(String businessConnectionId, String chatId, int messageId, InputChecklist checklist) {
        return new Builder().businessConnectionId(businessConnectionId).chatId(chatId).messageId(messageId).checklist(checklist);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "editMessageChecklist";
    }

    @Override
    public @NotNull Message deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Message.class);
    }

}
