package com.ausweglosigkeit.command;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class InformationAboutOfUser {
    protected int getMessageId(Update update) {
        return update.getCallbackQuery().getMessage().getMessageId();
    }

    protected long getLongChatId(Update update) {
        return update.getCallbackQuery().getMessage().getChatId();
    }

    protected String getStringChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId().toString();
        }
        else {
            return update.getCallbackQuery().getMessage().getChatId().toString();
        }
    }

    protected String getText(Update update) {
        return update.getMessage().getText();
    }

    protected Chat getChat(Update update) {
        return update.getMessage().getChat();
    }

    protected String getFirstName(Update update) {
        return getChat(update).getFirstName();
    }

    protected String getLastName(Update update) {
        return getChat(update).getLastName();
    }
}
