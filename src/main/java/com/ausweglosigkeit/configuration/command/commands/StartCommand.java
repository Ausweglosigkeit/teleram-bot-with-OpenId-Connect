package com.ausweglosigkeit.configuration.command.commands;


import com.ausweglosigkeit.button.ButtonAuthorizeYandex;
import com.ausweglosigkeit.configuration.command.InformationAboutOfUser;
import com.ausweglosigkeit.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ResourceBundle;


public class StartCommand extends InformationAboutOfUser implements Command {
    private final SendBotMessageService sendBotMessageService;
    private static final ResourceBundle BOT_DATA = ResourceBundle.getBundle("com.ausweglosigkeit.oidc.RegistrationData");
    private static final String clientID = BOT_DATA.getString("oidc.yandex.client.id");
    private static final String URI_AUTHORIZE = BOT_DATA.getString("oidc.yandex.uri.authorize");
    public static ButtonAuthorizeYandex button = new ButtonAuthorizeYandex(URI_AUTHORIZE + clientID);

    public static final String START_MESSAGE =
            "Здравствуйте.\nЧтобы продолжить взаимодействие с ботом авторизуйтесь, перейдя по ссылке";
    public static final String START_HELP = String.format("%s - начать работу", CommandName.START.getCommandName());

    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        SendMessage sendMessage = new SendMessage();
        button.attachKeyboard(sendMessage);

        sendBotMessageService.sendMessageWithInlineButton(sendMessage, getStringChatId(update), START_MESSAGE);
        button.detachKeyboard(sendMessage);
    }
}
