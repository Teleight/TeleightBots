package org.teleight.teleightbots.menu;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.event.EventManager;

import java.util.Collection;

public sealed interface MenuManager permits MenuManagerImpl {

    @NotNull EventManager getEventNode();

    void registerMenu(@NotNull Menu menu);

    @NotNull @Unmodifiable Collection<Menu> getMenus();

    @Nullable Menu getMenu(String name);

    void unregisterMenu(int menuId);

}
