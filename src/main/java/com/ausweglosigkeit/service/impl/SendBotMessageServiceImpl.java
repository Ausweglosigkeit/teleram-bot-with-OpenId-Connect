package com.ausweglosigkeit.service.impl;

import com.ausweglosigkeit.bot.AusBot;
import com.ausweglosigkeit.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SendBotMessageServiceImpl implements SendBotMessageService {
    private final AusBot ausBot;

    public SendBotMessageServiceImpl(AusBot ausBot) {
        this.ausBot = ausBot;
    }

    @Override
    public void sendMessage(String chatId, String message) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setParseMode("HTML");
        sendMessage.setText(message);

        execution(sendMessage);
    }

    @Override
    public void sendMessageWithInlineButton(SendMessage sendMessage, String chatId, String message) {

        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        execution(sendMessage);
    }

    private void execution(SendMessage sendMessage) {
        try {
            ausBot.execute(sendMessage);
        } catch (TelegramApiException exception){
            exception.printStackTrace();
        }
    }
}
