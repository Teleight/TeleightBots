package org.teleight.teleightbots.api.objects;

public record ReactionTypePaid() implements ReactionType {

    @Override
    public String type() {
        return "paid";
    }

}
