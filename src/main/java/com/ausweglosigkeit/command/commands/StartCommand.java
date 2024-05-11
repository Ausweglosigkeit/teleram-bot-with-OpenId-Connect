package com.ausweglosigkeit.command.commands;


import com.ausweglosigkeit.command.UserInfo;
import com.ausweglosigkeit.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.ausweglosigkeit.command.commands.CommandName.START;


public class StartCommand extends UserInfo implements Command {
    private final SendBotMessageService sendBotMessageService;

    public static final String START_MESSAGE = "Здравствуйте, %s.\nЧтобы продолжить взаимодействие с ботом авторизуйтесь, перейдя по ссылке: ....";
    public static final String START_HELP = String.format("%s - начать работу", START.getCommandName());

    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        String firstName = getFirstName(update);
        String lastName = getLastName(update);

        if (lastName == null) {
            sendBotMessageService.sendMessage(getStringChatId(update), String.format(START_MESSAGE, firstName));
        } else {
            String userName = firstName + " " + lastName;
            sendBotMessageService.sendMessage(getStringChatId(update), String.format(START_MESSAGE, userName));
        }
    }
}
