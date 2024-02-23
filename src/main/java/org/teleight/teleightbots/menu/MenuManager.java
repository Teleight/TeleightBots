package org.teleight.teleightbots.menu;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.event.EventManager;

import java.util.Collection;

/**
 * Interface for a Menu Manager that provides methods for managing menus in the bot application.
 */
public sealed interface MenuManager permits MenuManagerImpl {

    /**
     * Gets the EventManager associated with this MenuManager.
     *
     * @return the EventManager associated with this MenuManager
     */
    @NotNull EventManager getEventNode();

    /**
     * Registers a new Menu with this MenuManager.
     *
     * @param menu the Menu to be registered
     */
    void registerMenu(@NotNull Menu menu);

    /**
     * Gets all the Menus registered with this MenuManager.
     *
     * @return a collection of all the Menus registered with this MenuManager
     */
    @NotNull @Unmodifiable Collection<Menu> getMenus();

    /**
     * Gets a Menu registered with this MenuManager by its name.
     *
     * @param name the name of the Menu
     * @return the Menu with the given name, or null if no such Menu is registered
     */
    @Nullable Menu getMenu(String name);

    /**
     * Unregisters a Menu from this MenuManager by its ID.
     *
     * @param menuId the ID of the Menu to be unregistered
     */
    void unregisterMenu(int menuId);

}
