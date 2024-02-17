package org.teleight.teleightbots.api.objects.menubutton;

public record MenuButtonDefault(
) implements MenuButton {

    @Override
    public String type() {
        return "default";
    }

}
