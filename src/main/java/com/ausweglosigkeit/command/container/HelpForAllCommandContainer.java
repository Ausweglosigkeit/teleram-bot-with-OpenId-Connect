package com.ausweglosigkeit.command.container;

import com.google.common.collect.ImmutableMap;

import static com.ausweglosigkeit.command.commands.CommandName.START;
import static com.ausweglosigkeit.command.commands.StartCommand.START_HELP;

public class HelpForAllCommandContainer {
    private final ImmutableMap<String, String> containerHelpForCommand;
    private final String unknownCommand = "У этой команды нет Help`a";

    public HelpForAllCommandContainer() {
        containerHelpForCommand = ImmutableMap.<String, String>builder()
                .put(START.getCommandName(), START_HELP)
                .build();
    }

    public String getHelpForCommand(String commandName) {
        return containerHelpForCommand.getOrDefault(commandName, unknownCommand);
    }
}
