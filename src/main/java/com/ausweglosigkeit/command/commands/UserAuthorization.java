package com.ausweglosigkeit.command.commands;



import com.ausweglosigkeit.bot.AusBot;
import com.ausweglosigkeit.command.InformationAboutOfUser;
import com.ausweglosigkeit.oidc.AuthorizationUsingYandex;
import com.ausweglosigkeit.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UserAuthorization extends InformationAboutOfUser implements Command {
    private final SendBotMessageService sendBotMessageService;

    private static final String AUTHORIZATION_MESSAGE = "Вы успешно авторизовались.";

    public UserAuthorization(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        AuthorizationUsingYandex authorizationUsingYandex = new AuthorizationUsingYandex(getText(update));
        if (authorizationUsingYandex.getCodeError() == 0) {
            sendBotMessageService.sendMessage(getStringChatId(update), AUTHORIZATION_MESSAGE);
            AusBot.cleanLastCommand();
        } else {
            sendBotMessageService.sendMessage(getStringChatId(update), "неправильный код!");
        }
    }
}
