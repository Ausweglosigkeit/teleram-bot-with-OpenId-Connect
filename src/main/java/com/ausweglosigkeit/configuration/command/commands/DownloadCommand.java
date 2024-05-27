package com.ausweglosigkeit.configuration.command.commands;

import com.ausweglosigkeit.configuration.command.InformationAboutOfUser;
import com.ausweglosigkeit.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class DownloadCommand extends InformationAboutOfUser implements Command{
    private final SendBotMessageService sendBotMessageService;
    private static final String DOWNLOAD_MESSAGE = "Для скачивания видео отправте ссылку на видео, которое хотите скачать.";

    public static final String DOWNLOAD_HELP = "%s - скачать видео с youtube";

    public DownloadCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(getStringChatId(update), DOWNLOAD_MESSAGE);
    }
}
