package com.ausweglosigkeit.command.container;

import com.ausweglosigkeit.command.commands.*;
import com.ausweglosigkeit.service.SendBotMessageService;
import com.google.common.collect.ImmutableMap;

import static com.ausweglosigkeit.command.commands.CommandName.*;

public class CommandContainer {
    private final Command unknownCommand;
    private final ImmutableMap<String, Command> commandImmutableMap;

    public CommandContainer(SendBotMessageService sendBotMessageService) {
        commandImmutableMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .put(DOWNLOAD.getCommandName(), new DownloadCommand(sendBotMessageService))
                .build();
        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandImmutableMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
