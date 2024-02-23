package org.teleight.teleightbots.menu;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.objects.keyboard.InlineKeyboardMarkup;

import java.util.List;

/**
 * This is an interface for a Menu. It provides methods to manipulate and retrieve information about the menu.
 * @see org.teleight.teleightbots.bot.trait.TelegramBot#createMenu(String, Builder)
 * @see org.teleight.teleightbots.bot.trait.TelegramBot#createMenu(Builder)
 * @see org.teleight.teleightbots.menu.MenuButton
 * @see org.teleight.teleightbots.menu.MenuBuilder
 */
public interface Menu {

    /**
     * This method returns the ID of the menu.
     * @return The ID of the menu.
     */
    int getMenuId();

    /**
     * This method returns the name of the menu.
     * @return The name of the menu, or null if it is not set.
     */
    @Nullable String getMenuName();

    /**
     * This method returns the text of the menu.
     * @return The text of the menu, or null if it is not set.
     */
    @Nullable String getText();

    /**
     * This method sets the text of the menu.
     * @param text The text to set.
     * @return The Menu instance.
     */
    @NotNull Menu setText(@NotNull String text);

    /**
     * This method adds a row of MenuButtons to the menu.
     * @param buttons The MenuButtons to add.
     * @return The Menu instance.
     */
    @NotNull Menu addRow(@NotNull MenuButton... buttons);

    /**
     * This method adds a row of MenuButtons to the menu.
     * @param buttons The list of MenuButtons to add.
     * @return The Menu instance.
     */
    @NotNull Menu addRow(@NotNull List<MenuButton> buttons);

    /**
     * This method returns the keyboard of the menu.
     * @return The keyboard of the menu.
     */
    @NotNull InlineKeyboardMarkup getKeyboard();

    @FunctionalInterface
    interface Builder {
        /**
         * This method creates a Menu.
         * @param context The MenuBuilder context.
         * @param rootMenu The root Menu.
         */
        void create(@NotNull MenuBuilder context, @NotNull Menu rootMenu);
    }

}
