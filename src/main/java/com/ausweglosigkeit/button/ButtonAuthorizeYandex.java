package com.ausweglosigkeit.button;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ButtonAuthorizeYandex {
    private static final InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
    private static final List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
    private static final List<InlineKeyboardButton> buttonAuth = new ArrayList<>();
    private static final String textButton = "Авторизоваться с помощью Яндекс аккаунта";
    private static final String buttonId = "authorize";
    private final String URI;

    public ButtonAuthorizeYandex(String URI) {
        this.URI = URI;
        createButton();
    }

    public void attachKeyboard(SendMessage sendMessage) {
        sendMessage.setReplyMarkup(inlineKeyboard);
    }

    public void detachKeyboard(SendMessage sendMessage) {
        sendMessage.setReplyMarkup(null);
    }

    private void createButton() {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(textButton);
        button.setUrl(URI);

        buttonAuth.add(button);
        buttons.add(buttonAuth);
        inlineKeyboard.setKeyboard(buttons);
    }

}
