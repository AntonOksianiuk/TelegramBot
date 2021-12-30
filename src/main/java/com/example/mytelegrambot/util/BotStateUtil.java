package com.example.mytelegrambot.util;

import com.example.mytelegrambot.configuration.BotState;

public class BotStateUtil {

    public static BotState getMainBotState(BotState botState) {
        switch (botState) {
            case CREATE_CITY:
            case ASK_CITYNAME:
            case ASK_DESCRIPTION:
                return BotState.CREATE_CITY;

            case DELETE_CITY:
                return BotState.DELETE_CITY;

            default:
                return BotState.DEFAULT;
        }
    }
}
