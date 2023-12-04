package org.teleight.teleightbots.menu;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.api.methods.callback.CallbackQuery;
import org.teleight.teleightbots.api.methods.inline.EditMessageReplyMarkup;
import org.teleight.teleightbots.api.methods.inline.EditMessageText;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.chat.Chat;
import org.teleight.teleightbots.api.objects.keyboard.InlineKeyboardMarkup;
import org.teleight.teleightbots.event.EventManager;
import org.teleight.teleightbots.event.EventManagerImpl;
import org.teleight.teleightbots.event.keyboard.ButtonPressEvent;

import java.util.*;

public final class MenuManagerImpl implements MenuManager {

    private final EventManager eventManager = new EventManagerImpl();
    private final Map<Integer, Menu> menus = new HashMap<>();

    public MenuManagerImpl() {
        eventManager.addListener(ButtonPressEvent.class, event -> {
            for (final Menu menu : getMenus()) {
                handleMenu((MenuImpl) menu, event);
            }
        });
    }

    private void handleMenu(@NotNull MenuImpl menu, @NotNull ButtonPressEvent event) {
        final List<List<MenuButton>> internalColumns = menu.getColumns();
        for (final List<MenuButton> columns : internalColumns) {
            for (final MenuButton buttonInRow : columns) {
                handleButton(menu, buttonInRow, event);
            }
        }
    }

    private void handleButton(@NotNull Menu menu, @NotNull MenuButton rowButton, @NotNull ButtonPressEvent event) {
        final CallbackQuery callbackQuery = event.callbackQuery();
        final Message message = callbackQuery.message();
        final Chat chat = message.chat();
        final String chatId = "" + chat.id();
        final String callbackData = callbackQuery.data();
        final String inlineMessageId = callbackQuery.inlineMessageId();
        final int messageId = message.messageId();

        if(callbackData == null){
            return;
        }

        final boolean hasClickedThisButton = callbackData.equals(rowButton.callbackData());
        if (!hasClickedThisButton) {
            return;
        }
        final boolean hasCallback = rowButton.callback() != null;
        final boolean hasDestinationMenu = rowButton.destinationMenu() != null;

        if(hasCallback) {
            rowButton.callback().accept(event, event.callbackQuery().from());
            return;
        }


        if(hasDestinationMenu) {
            final Menu destinationMenu = rowButton.destinationMenu();
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
            return;
        }
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
    public @Nullable Menu getMenu(String name) {
        for (Menu value : menus.values()) {
            if(value.getMenuName().equalsIgnoreCase(name)){
                return value;
            }
        }
        return null;
    }

    @Override
    public void unregisterMenu(int menuId) {
        if (!menus.containsKey(menuId)) {
            throw new IllegalArgumentException("The menu with id " + menuId + " doesn't exist");
        }
        menus.remove(menuId);
    }

}
