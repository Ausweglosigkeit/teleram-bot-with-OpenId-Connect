package com.ausweglosigkeit.bot;

import com.ausweglosigkeit.command.container.CommandContainer;
import com.ausweglosigkeit.command.container.LastCommandContainer;
import com.ausweglosigkeit.service.impl.SendBotMessageServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
public class AusBot extends TelegramLongPollingBot {
    private final CommandContainer commandContainer;
    private final LastCommandContainer lastCommandContainer;
    private static final String COMMAND_PREFIX = "/";
    private static String LAST_COMMAND;


    public AusBot(@Value("${bot.token}") String botToken) {
        super(botToken);
        commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this));
        lastCommandContainer = new LastCommandContainer(new SendBotMessageServiceImpl(this));
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();
                LAST_COMMAND = commandIdentifier;
                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                lastCommandContainer.retrieveCommand(LAST_COMMAND).execute(update);
            }
        } else if (update.hasCallbackQuery()) {
            String commandIdentifier = update.getCallbackQuery().getData().split(" ")[1].toLowerCase();
            lastCommandContainer.retrieveCommand(commandIdentifier).execute(update);
        }
    }

    @Value("${bot.username}") String usernameBot;
    @Override
    public String getBotUsername() {
        return usernameBot;
    }

    public static void cleanLastCommand() {
        LAST_COMMAND = null;
    }
}
