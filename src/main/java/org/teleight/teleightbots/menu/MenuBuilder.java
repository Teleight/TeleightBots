package org.teleight.teleightbots.menu;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@FunctionalInterface
public interface MenuBuilder {

    /**
     * This method creates a Menu with a given name.
     *
     * @param name The name of the Menu to create.
     * @return The created Menu.
     */
    @NotNull Menu createMenu(@Nullable String name);

    /**
     * This is an implementation of the MenuBuilder interface. It provides a method to create a menu and get all created menus.
     */
    class MenuBuilderImpl implements MenuBuilder {
        // A list of all created menus
        private final List<MenuImpl> allMenus = new LinkedList<>();

        /**
         * This method creates a Menu with a given name and adds it to the list of all menus.
         *
         * @param name The name of the Menu to create.
         * @return The created Menu.
         */
        @Override
        public @NotNull Menu createMenu(@Nullable String name) {
            MenuImpl menuImpl = new MenuImpl(name);
            allMenus.add(menuImpl);
            return menuImpl;
        }

        /**
         * This method returns an unmodifiable list of all created menus.
         *
         * @return An unmodifiable list of all created menus.
         */
        public @NotNull @Unmodifiable List<MenuImpl> getAllMenus() {
            return Collections.unmodifiableList(allMenus);
        }
    }

}
