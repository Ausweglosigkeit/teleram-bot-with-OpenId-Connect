package com.ausweglosigkeit.configuration.command.commands;

import com.ausweglosigkeit.configuration.command.InformationAboutOfUser;
import com.ausweglosigkeit.configuration.command.container.HelpForAllCommandContainer;
import com.ausweglosigkeit.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelpCommand extends InformationAboutOfUser implements Command {
    private final SendBotMessageService sendBotMessageService;
    private static final String HELP_REGEX = "^(/help *) (/[a-z]+) *$";
    private static final HelpForAllCommandContainer HELP_FOR_ALL_COMMAND_CONTAINER = new HelpForAllCommandContainer();

    public static final String HELP_MESSAGE = "какой-то текст с хелпом ";

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        String message = getText(update);
        Pattern pattern = Pattern.compile(HELP_REGEX);
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            String commandName = matcher.group(2);
            String helpForCommand = HELP_FOR_ALL_COMMAND_CONTAINER.getHelpForCommand(commandName);
            sendBotMessageService.sendMessage(getStringChatId(update), helpForCommand);
        } else {
            sendBotMessageService.sendMessage(getStringChatId(update), HELP_MESSAGE);
        }
    }
}
