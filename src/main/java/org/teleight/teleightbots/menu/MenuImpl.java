package org.teleight.teleightbots.menu;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.keyboard.InlineKeyboardButton;
import org.teleight.teleightbots.api.objects.keyboard.InlineKeyboardMarkup;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@ApiStatus.Internal
public class MenuImpl implements Menu {

    private static final AtomicInteger LAST_MENU_ID = new AtomicInteger();
    private final int menuId = LAST_MENU_ID.incrementAndGet();
    private final String menuName;

    //Keyboard
    private final List<List<MenuButton>> columns = new LinkedList<>();
    private String text;
    private InlineKeyboardMarkup keyboard;

    public MenuImpl(@NotNull String menuName) {
        this.menuName = menuName;
    }

    @Override
    public @NotNull Menu addRow(@NotNull MenuButton... buttons) {
        final List<MenuButton> column = new LinkedList<>(List.of(buttons));
        columns.add(column);
        return this;
    }

    @Override
    public int getMenuId() {
        return menuId;
    }

    @Override
    public @NotNull String getMenuName() {
        return menuName;
    }

    @Override
    public @NotNull String getText() {
        return text;
    }

    @Override
    public void setText(@NotNull String text) {
        this.text = text;
    }

    @Override
    public @NotNull InlineKeyboardMarkup getKeyboard() {
        return keyboard;
    }

    public void createKeyboard() {
        final InlineKeyboardButton[][] newColumns = new InlineKeyboardButton[columns.size()][];
        for (int columnIndex = 0; columnIndex < columns.size(); columnIndex++) {
            final List<MenuButton> row = columns.get(columnIndex);
            newColumns[columnIndex] = new InlineKeyboardButton[row.size()];

            for (int rowIndex = 0; rowIndex < row.size(); rowIndex++) {
                final MenuButton oldButton = row.get(rowIndex);
                newColumns[columnIndex][rowIndex] = new InlineKeyboardButton(oldButton.text(), oldButton.callbackData());
            }
        }

        keyboard = new InlineKeyboardMarkup(newColumns);
    }

}
