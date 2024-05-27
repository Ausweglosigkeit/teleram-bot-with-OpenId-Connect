package com.ausweglosigkeit.configuration.command.commands;

import com.ausweglosigkeit.configuration.command.InformationAboutOfUser;
import com.ausweglosigkeit.loader.Loader;
import com.ausweglosigkeit.service.SendBotMessageService;
import com.ausweglosigkeit.user.UserData;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownloadVideo extends InformationAboutOfUser implements Command {
    private final SendBotMessageService sendBotMessageService;
    private String videoID;
    private static final String REGEX = "v=(.*)(&t=.*)?";
    private static final String DOWNLOAD_MESSAGE = "скачаивание началось";
    private static final String SUCCESSFULLY_MESSAGE = "Видео успешно скачалось.\nСейчас я Вам его отправлю.";
    private static final String UNSUCCESSFULLY_MESSAGE = "Видео не удалось скачать :(";
    private static final ResourceBundle BOT_DATA = ResourceBundle.getBundle("com.ausweglosigkeit.oidc.RegistrationData");

    public DownloadVideo(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatID = getStringChatId(update);
        sendBotMessageService.sendMessage(chatID, DOWNLOAD_MESSAGE);
        videoID = setVideoID(getText(update));
        String login = UserData.getLogin(chatID);
        if (videoID != null) {
            try {
                new Loader(videoID, login);
            } catch (NullPointerException e) {
                sendBotMessageService.sendMessage(chatID, UNSUCCESSFULLY_MESSAGE);
                return;
            }
            sendBotMessageService.sendMessage(chatID, SUCCESSFULLY_MESSAGE);
            sendBotMessageService.sendVideo(chatID,BOT_DATA.getString("loader.path.videofile") + "\\" + login + ".mp4");
            return;
        }
        sendBotMessageService.sendMessage(chatID, UNSUCCESSFULLY_MESSAGE);
    }

    private String setVideoID(String link) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(link);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
