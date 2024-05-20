package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record ChatPermissions(
        @JsonProperty("can_send_messages")
        boolean canSendMessages,

        @JsonProperty("can_send_audios")
        boolean canSendAudios,

        @JsonProperty("can_send_documents")
        boolean canSendDocuments,

        @JsonProperty("can_send_photos")
        boolean canSendPhotos,

        @JsonProperty("can_send_videos")
        boolean canSendVideos,

        @JsonProperty("can_send_video_notes")
        boolean canSendVideoNotes,

        @JsonProperty("can_send_voice_notes")
        boolean canSendVoiceNotes,

        @JsonProperty("can_send_polls")
        boolean canSendPolls,

        @JsonProperty("can_send_other_messages")
        boolean canSendOtherMessages,

        @JsonProperty("can_add_web_page_previews")
        boolean canAddWebPagePreviews,

        @JsonProperty("can_change_info")
        boolean canChangeInfo,

        @JsonProperty("can_invite_users")
        boolean canInviteUsers,

        @JsonProperty("can_pin_messages")
        boolean canPinMessages,

        @JsonProperty("can_manage_topics")
        boolean canManageTopics
) implements ApiResult {
}
