package org.teleight.teleightbots.menu;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.objects.InlineKeyboardButton;
import org.teleight.teleightbots.api.objects.InlineKeyboardMarkup;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@ApiStatus.Internal
public class MenuImpl implements Menu {

    private static final AtomicInteger LAST_MENU_ID = new AtomicInteger();
    private final int menuId = LAST_MENU_ID.incrementAndGet();
    private final String menuName;

    //Keyboard
    private final List<List<InlineKeyboardButton>> columns = new LinkedList<>();
    private String text;
    private InlineKeyboardMarkup keyboard;

    public MenuImpl(@Nullable String menuName) {
        this.menuName = menuName;
    }

    @Override
    public @NotNull Menu addRow(@NotNull InlineKeyboardButton... buttons) {
        return addRow(List.of(buttons));
    }

    @Override
    public @NotNull Menu addRow(@NotNull List<InlineKeyboardButton> buttons) {
        final List<InlineKeyboardButton> column = new LinkedList<>(buttons);
        columns.add(column);
        return this;
    }

    @Override
    public int getMenuId() {
        return menuId;
    }

    @Override
    public @Nullable String getMenuName() {
        return menuName;
    }

    @Override
    public @NotNull String getText() {
        return text;
    }

    @Override
    public @NotNull Menu setText(@NotNull String text) {
        this.text = text;
        return this;
    }

    @Override
    public @NotNull InlineKeyboardMarkup getKeyboard() {
        return keyboard;
    }

    @ApiStatus.Internal
    public void createKeyboard() {
        final InlineKeyboardButton[][] newColumns = new InlineKeyboardButton[columns.size()][];
        for (int columnIndex = 0; columnIndex < columns.size(); columnIndex++) {
            final List<InlineKeyboardButton> row = columns.get(columnIndex);
            newColumns[columnIndex] = new InlineKeyboardButton[row.size()];

            for (int rowIndex = 0; rowIndex < row.size(); rowIndex++) {
                final InlineKeyboardButton oldButton = row.get(rowIndex);

//                if (oldButton.url() == null || oldButton.callbackData() == null) {
//                    throw new IllegalStateException("Button " + oldButton + " has no callback data or url");
//                }

                final InlineKeyboardButton newButton = InlineKeyboardButton.ofBuilder(oldButton.text())
                        .url(oldButton.url())
                        .callbackData(oldButton.callbackData())
                        .build();
                newColumns[columnIndex][rowIndex] = newButton;
            }
        }

        keyboard = new InlineKeyboardMarkup(newColumns);
    }

    @ApiStatus.Internal
    List<List<InlineKeyboardButton>> getColumns() {
        return columns;
    }

}
