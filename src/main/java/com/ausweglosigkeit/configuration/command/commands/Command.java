package com.ausweglosigkeit.configuration.command.commands;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    void execute(Update update);
}
