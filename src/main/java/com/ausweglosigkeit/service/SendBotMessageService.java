package com.ausweglosigkeit.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface SendBotMessageService {
    void sendMessage(String chatId, String message);
    void sendVideo(String chatId, String videoPath);
    void sendMessageWithInlineButton(SendMessage sendMessage, String chatId, String message);
}
