package org.teleight.teleightbots.menu;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@FunctionalInterface
public interface MenuBuilder {

    @NotNull Menu createMenu(@NotNull String name);

    class MenuBuilderImpl implements MenuBuilder {
        private final List<MenuImpl> allMenus = new LinkedList<>();

        @Override
        public @NotNull Menu createMenu(@NotNull String name) {
            MenuImpl menuImpl = new MenuImpl(name);
            allMenus.add(menuImpl);
            return menuImpl;
        }

        public @NotNull @Unmodifiable List<MenuImpl> getAllMenus() {
            return Collections.unmodifiableList(allMenus);
        }
    }

}
