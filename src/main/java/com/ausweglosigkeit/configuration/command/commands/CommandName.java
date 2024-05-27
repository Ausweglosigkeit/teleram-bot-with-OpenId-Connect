package com.ausweglosigkeit.configuration.command.commands;

public enum CommandName {
    START("/start"),
    HELP("/help"),
    NO("/nocommand"),
    DOWNLOAD("/download");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
