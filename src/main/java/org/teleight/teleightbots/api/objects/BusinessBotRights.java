package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record BusinessBotRights(
        @JsonProperty(value = "can_reply")
        boolean canReply,

        @JsonProperty(value = "can_read_messages")
        boolean canReadMessages,

        @JsonProperty(value = "can_delete_outgoing_messages")
        boolean canDeleteOutgoingMessages,

        @JsonProperty(value = "can_delete_all_messages")
        boolean canDeleteAllMessages,

        @JsonProperty(value = "can_edit_name")
        boolean canEditName,

        @JsonProperty(value = "can_edit_bio")
        boolean canEditBio,

        @JsonProperty(value = "can_edit_profile_photo")
        boolean canEditProfilePhoto,

        @JsonProperty(value = "can_edit_username")
        boolean canEditUsername,

        @JsonProperty(value = "can_change_gift_settings")
        boolean canChangeGiftSettings,

        @JsonProperty(value = "can_view_gifts_and_stars")
        boolean canViewGiftsAndStars,

        @JsonProperty(value = "can_convert_gifts_to_stars")
        boolean canConvertGiftsToStars,

        @JsonProperty(value = "can_transfer_and_upgrade_gifts")
        boolean canTransferAndUpgradeGifts,

        @JsonProperty(value = "can_transfer_stars")
        boolean canTransferStars,

        @JsonProperty(value = "can_manage_stories")
        boolean canManageStories
) implements ApiResult {
}
