package com.ausweglosigkeit.command.container;

import com.ausweglosigkeit.command.commands.*;
import com.ausweglosigkeit.service.SendBotMessageService;
import com.google.common.collect.ImmutableMap;

import static com.ausweglosigkeit.command.commands.CommandName.*;

public class LastCommandContainer {
    private Command noCommand;
    private ImmutableMap<String, Command> lastCommandMap;

    public LastCommandContainer(SendBotMessageService sendBotMessageService) {
        lastCommandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new UserAuthorization(sendBotMessageService))
                .build();
        noCommand = new NoCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return lastCommandMap.getOrDefault(commandIdentifier, noCommand);
    }
}
