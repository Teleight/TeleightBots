package org.teleight.teleightbots.api.pagination;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.objects.keyboard.InlineKeyboardMarkup;

public interface Menu {

    int getMenuId();

    @NotNull String getMenuName();

    @Nullable String getText();

    void setText(@NotNull String text);

    @NotNull Menu addRow(@NotNull MenuButton... buttons);

    @NotNull InlineKeyboardMarkup getKeyboard();

    @FunctionalInterface
    interface Builder {
        void create(@NotNull MenuBuilder context, @NotNull Menu rootMenu);
    }

}
