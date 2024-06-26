package org.teleight.teleightbots.api.objects;

public record MenuButtonCommands() implements MenuButton {

    @Override
    public MenuButtonType type() {
        return MenuButtonType.COMMANDS;
    }

}
