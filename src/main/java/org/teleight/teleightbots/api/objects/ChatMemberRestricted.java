package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChatMemberRestricted(
        @JsonProperty(value = "user")
        User user,

        @JsonProperty(value = "is_member")
        boolean isMember,

        @JsonProperty(value = "can_send_messages")
        boolean canSendMessages,

        @JsonProperty(value = "can_send_audios")
        boolean canSendAudios,

        @JsonProperty(value = "can_send_documents")
        boolean canSendDocuments,

        @JsonProperty(value = "can_send_videos")
        boolean canSendVideos,

        @JsonProperty(value = "can_send_photos")
        boolean canSendPhotos,

        @JsonProperty(value = "can_send_video_notes")
        boolean canSendVideoNotes,

        @JsonProperty(value = "can_send_voice_notes")
        boolean canSendVoiceNotes,

        @JsonProperty(value = "can_send_polls")
        boolean canSendPolls,

        @JsonProperty(value = "can_send_other_messages")
        boolean canSendOtherMessages,

        @JsonProperty(value = "can_add_web_page_previews")
        boolean canAddWebPagePreviews,

        @JsonProperty(value = "can_change_info")
        boolean canChangeInfo,

        @JsonProperty(value = "can_invite_users")
        boolean canInviteUsers,

        @JsonProperty(value = "can_pin_messages")
        boolean canPinMessages,

        @JsonProperty(value = "can_manage_topics")
        boolean canManageTopics,

        @JsonProperty(value = "until_date")
        int untilDate
) implements ChatMember {

    @Override
    public String status() {
            return "restricted";
    }

}
