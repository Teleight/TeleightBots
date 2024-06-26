package org.teleight.teleightbots.api.objects;

public record MenuButtonDefault() implements MenuButton {

    @Override
    public MenuButtonType type() {
        return MenuButtonType.DEFAULT;
    }

}
