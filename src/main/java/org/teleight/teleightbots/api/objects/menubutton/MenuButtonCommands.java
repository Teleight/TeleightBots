package org.teleight.teleightbots.api.objects.menubutton;

public record MenuButtonCommands(
) implements MenuButton{

    @Override
    public String type() {
        return "commands";
    }

}
