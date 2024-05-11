package com.ausweglosigkeit.command.commands;

public enum CommandName {
    START("/start"),
    HELP("/help"),
    NO("/nocommand");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
