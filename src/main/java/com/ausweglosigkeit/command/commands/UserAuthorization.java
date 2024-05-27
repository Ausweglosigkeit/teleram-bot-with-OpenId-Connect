package com.ausweglosigkeit.command.commands;


import com.ausweglosigkeit.bot.AusBot;
import com.ausweglosigkeit.command.InformationAboutOfUser;
import com.ausweglosigkeit.oidc.AuthorizationUsingYandex;
import com.ausweglosigkeit.service.SendBotMessageService;
import com.ausweglosigkeit.user.UserData;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;


public class UserAuthorization extends InformationAboutOfUser implements Command {
    private final SendBotMessageService sendBotMessageService;
    private Map<String, String> dataOfUser = new HashMap<>();

    private static final String AUTHORIZATION_MESSAGE = "Здравствуйте, %s.\nВы успешно авторизовались.";

    public UserAuthorization(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        AuthorizationUsingYandex authorizationUsingYandex = new AuthorizationUsingYandex(getText(update));
        if (authorizationUsingYandex.getCodeError() == 0) {
            dataOfUser = authorizationUsingYandex.getDataOfUser(update);
            if (UserData.addUser(getStringChatId(update), dataOfUser)) {
                sendBotMessageService.sendMessage(getStringChatId(update), String.format(AUTHORIZATION_MESSAGE, UserData.getLogin(getStringChatId(update))));
                sendBotMessageService.sendMessage(getStringChatId(update), "Теперь вы можете скачивать видео использовав команду /download");
                UserData.getInfo(getStringChatId(update));
                AusBot.cleanLastCommand();
            } else {
                sendBotMessageService.sendMessage(getStringChatId(update), String.format("%s, здравствуйте!\nНо вы уже зарегистрированы", UserData.getLogin(getStringChatId(update))));
            }
        } else {
            sendBotMessageService.sendMessage(getStringChatId(update), "неправильный код!");
        }
    }
}
