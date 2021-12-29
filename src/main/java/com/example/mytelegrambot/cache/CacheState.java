package com.example.mytelegrambot.cache;

import com.example.mytelegrambot.configuration.BotState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class CacheState {
    private Map<Long, BotState> botStateMap = new HashMap<>();

    public BotState getState(Long chatId){
        BotState currentBotState = botStateMap.get(chatId);

        if (currentBotState == null){
            setState(chatId, BotState.DEFAULT);
            return BotState.DEFAULT;
        }
        log.info(currentBotState.toString());

        return currentBotState;
    }

    public void setState(Long chatId, BotState botState){
        botStateMap.put(chatId, botState);
    }
}
