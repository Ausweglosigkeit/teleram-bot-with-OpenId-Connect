package com.ausweglosigkeit.configuration.command.container;

import com.ausweglosigkeit.configuration.command.commands.Command;
import com.ausweglosigkeit.configuration.command.commands.DownloadVideo;
import com.ausweglosigkeit.configuration.command.commands.NoCommand;
import com.ausweglosigkeit.configuration.command.commands.UserAuthorization;
import com.ausweglosigkeit.service.SendBotMessageService;
import com.google.common.collect.ImmutableMap;

import static com.ausweglosigkeit.configuration.command.commands.CommandName.*;

public class LastCommandContainer {
    private final Command noCommand;
    private final ImmutableMap<String, Command> lastCommandMap;

    public LastCommandContainer(SendBotMessageService sendBotMessageService) {
        lastCommandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new UserAuthorization(sendBotMessageService))
                .put(DOWNLOAD.getCommandName(), new DownloadVideo(sendBotMessageService))
                .build();
        noCommand = new NoCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return lastCommandMap.getOrDefault(commandIdentifier, noCommand);
    }
}
