package com.ausweglosigkeit.service.impl;

import com.ausweglosigkeit.bot.AusBot;
import com.ausweglosigkeit.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

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
    public void sendVideo(String chatId, String videoPath) {
        File video = new File(videoPath);
        InputFile inputFile = new InputFile(video);
        SendVideo sendVideo = new SendVideo();

        sendVideo.setChatId(chatId);
        sendVideo.setVideo(inputFile);

        execution(sendVideo);
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

    private void execution(SendVideo sendVideo) {
        try {
            ausBot.execute(sendVideo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
