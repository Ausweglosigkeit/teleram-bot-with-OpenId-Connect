package com.ausweglosigkeit.configuration.command.commands;

import com.ausweglosigkeit.configuration.command.InformationAboutOfUser;
import com.ausweglosigkeit.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class NoCommand extends InformationAboutOfUser implements Command{
    private final SendBotMessageService sendBotMessageService;

    public static final String NO_COMMAND_MESSAGE = "Я не понимаю, что вы хотите :С";

    public NoCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(getStringChatId(update), NO_COMMAND_MESSAGE);
    }
}
