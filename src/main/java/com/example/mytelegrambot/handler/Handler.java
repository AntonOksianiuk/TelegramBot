package com.example.mytelegrambot.handler;

import com.example.mytelegrambot.configuration.BotState;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface Handler {
    public String handle(Message message, BotState currentBotState);
}
