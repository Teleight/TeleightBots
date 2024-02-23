package org.teleight.teleightbots.menu;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.objects.keyboard.InlineKeyboardMarkup;

import java.util.List;

/**
 * Basic interface for a Menu. It provides methods to manipulate and retrieve information about the menu.
 * @see org.teleight.teleightbots.bot.trait.TelegramBot#createMenu(String, Builder)
 * @see org.teleight.teleightbots.bot.trait.TelegramBot#createMenu(Builder)
 * @see org.teleight.teleightbots.menu.MenuButton
 * @see org.teleight.teleightbots.menu.MenuBuilder
 */
public interface Menu {

    /**
     * @return The ID of the menu.
     */
    int getMenuId();

    /**
     * @return The name of the menu, or null if it is not set.
     */
    @Nullable String getMenuName();

    /**
     * @return The text of the menu, or null if it is not set.
     */
    @Nullable String getText();

    /**
     * Sets the text of the menu.
     * @param text The text to set.
     * @return The Menu instance.
     */
    @NotNull Menu setText(@NotNull String text);

    /**
     * Adds a row of MenuButtons to the menu.
     * @param buttons The MenuButtons to add.
     * @return The Menu instance.
     */
    @NotNull Menu addRow(@NotNull MenuButton... buttons);

    /**
     * Adds a row of MenuButtons to the menu.
     * @param buttons The list of MenuButtons to add.
     * @return The Menu instance.
     */
    @NotNull Menu addRow(@NotNull List<MenuButton> buttons);

    /**
     * Returns the keyboard of the menu.
     * @return The keyboard of the menu.
     */
    @NotNull InlineKeyboardMarkup getKeyboard();

    @FunctionalInterface
    interface Builder {
        /**
         * Creates a Menu from the given MenuBuilder context and root Menu.
         * @param context The MenuBuilder context.
         * @param rootMenu The root Menu.
         */
        void create(@NotNull MenuBuilder context, @NotNull Menu rootMenu);
    }

}
