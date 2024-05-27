package com.ausweglosigkeit.command.commands;

public enum CommandName {
    START("/start"),
    HELP("/help"),
    NO("/nocommand"),
    DOWNLOAD("/download"),
    UNAUTHORIZED("неавторизованный пользователь");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
