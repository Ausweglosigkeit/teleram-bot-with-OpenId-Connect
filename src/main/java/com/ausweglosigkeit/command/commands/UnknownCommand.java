package com.ausweglosigkeit.command.commands;


import com.ausweglosigkeit.command.InformationAboutOfUser;
import com.ausweglosigkeit.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.ausweglosigkeit.command.commands.CommandName.HELP;

public class UnknownCommand extends InformationAboutOfUser implements Command{
    private final SendBotMessageService sendBotMessageService;

    public static final String UNKNOWN_MESSAGE = String.format("Не понимаю тебя, напиши пожалуйста %s", HELP.getCommandName());

    public UnknownCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(getStringChatId(update), UNKNOWN_MESSAGE);
    }
}
