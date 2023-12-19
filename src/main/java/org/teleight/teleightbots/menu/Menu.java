package org.teleight.teleightbots.menu;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.objects.keyboard.InlineKeyboardMarkup;

import java.util.List;

public interface Menu {

    int getMenuId();

    @NotNull String getMenuName();

    @Nullable String getText();

    @NotNull Menu setText(@NotNull String text);

    @NotNull Menu addRow(@NotNull MenuButton... buttons);

    @NotNull Menu addRow(@NotNull List<MenuButton> buttons);

    @NotNull InlineKeyboardMarkup getKeyboard();

    @FunctionalInterface
    interface Builder {
        void create(@NotNull MenuBuilder context, @NotNull Menu rootMenu);
    }

}
