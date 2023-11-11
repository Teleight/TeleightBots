package org.teleight.teleightbots.menu;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.api.methods.callback.CallbackQuery;
import org.teleight.teleightbots.api.methods.inline.EditMessageReplyMarkup;
import org.teleight.teleightbots.api.methods.inline.EditMessageText;
import org.teleight.teleightbots.api.objects.chat.Chat;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.keyboard.InlineKeyboardButton;
import org.teleight.teleightbots.api.objects.keyboard.InlineKeyboardMarkup;
import org.teleight.teleightbots.event.EventManager;
import org.teleight.teleightbots.event.EventManagerImpl;
import org.teleight.teleightbots.event.keyboard.ButtonPressEvent;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class MenuManagerImpl implements MenuManager {

    private final EventManager eventManager = new EventManagerImpl();
    private final Map<Integer, Menu> menus = new HashMap<>();

    public MenuManagerImpl() {
        eventManager.addListener(ButtonPressEvent.class, event -> {
            for (final Menu menu : getMenus()) {
                handleMenu(menu, event);
            }
        });
    }

    private void handleMenu(@NotNull Menu menu, @NotNull ButtonPressEvent event) {
        final InlineKeyboardMarkup internalColumns = menu.getKeyboard();
        for (final InlineKeyboardButton[] columns : internalColumns.keyboard()) {
            for (final InlineKeyboardButton buttonInRow : columns) {
                handleButton(buttonInRow, event);
            }
        }
    }

    private void handleButton(@NotNull InlineKeyboardButton rowButton, @NotNull ButtonPressEvent event) {
        final CallbackQuery callbackQuery = event.callbackQuery();
        final Message message = callbackQuery.message();
        final Chat chat = message.chat();
        final String chatId = "" + chat.id();
        final String callbackData = callbackQuery.data();
        final String inlineMessageId = callbackQuery.inlineMessageId();
        final int messageId = message.messageId();

        final boolean hasClickedThisButton = callbackData.equals(rowButton.callbackData());
        if (!hasClickedThisButton) {
            return;
        }
        if (!callbackData.contains(MenuButton.MENU_ID_PARAM)) {
            return;
        }
        final String destinationMenuAsString = extractValue(callbackData, MenuButton.MENU_ID_PARAM);
        if(destinationMenuAsString == null){
            return;
        }

        int destinationMenuId = -1;
        try {
            destinationMenuId = Integer.parseInt(destinationMenuAsString);
        } catch (Exception ignored) {
        }

        final Menu destinationMenu = menus.get(destinationMenuId);
        if (destinationMenu == null) {
            return;
        }
        final InlineKeyboardMarkup keyboard = destinationMenu.getKeyboard();
        final String text = destinationMenu.getText();
        final boolean shouldChangeText = text != null;

        if (shouldChangeText) {
            final EditMessageText editMessageText = EditMessageText.builder()
                    .text(text)
                    .chatId(chatId)
                    .messageId(messageId)
                    .replyMarkup(keyboard)
                    .build();
            event.execute(editMessageText);
        } else {
            final EditMessageReplyMarkup editMessageReplyMarkup = EditMessageReplyMarkup.builder()
                    .chatId(chatId)
                    .messageId(messageId)
                    .inlineMessageId(inlineMessageId)
                    .replyMarkup(keyboard)
                    .build();
            event.execute(editMessageReplyMarkup);
        }
    }

    private @Nullable String extractValue(@NotNull String queryString, @NotNull String key) {
        final int keyStart = queryString.indexOf(key);
        if (keyStart == -1) {
            return null;
        }
        final int valueStart = keyStart + key.length();
        int valueEnd = queryString.indexOf('&', valueStart);
        if (valueEnd == -1) {
            valueEnd = queryString.length();
        }
        return queryString.substring(valueStart, valueEnd);
    }

    @Override
    public @NotNull EventManager getEventNode() {
        return eventManager;
    }

    @Override
    public void registerMenu(@NotNull Menu menu) {
        if (menus.containsKey(menu.getMenuId())) {
            throw new IllegalArgumentException("The menu " + menu.getMenuName() + " has already been registered");
        }
        menus.put(menu.getMenuId(), menu);
    }

    @Override
    public @NotNull @Unmodifiable Collection<Menu> getMenus() {
        return Collections.unmodifiableCollection(menus.values());
    }

    @Override
    public void unregisterMenu(int menuId) {
        if (!menus.containsKey(menuId)) {
            throw new IllegalArgumentException("The menu with id " + menuId + " doesn't exist");
        }
        menus.remove(menuId);
    }

}
