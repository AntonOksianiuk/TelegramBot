package com.example.mytelegrambot.handler;

import com.example.mytelegrambot.cache.CacheState;
import com.example.mytelegrambot.configuration.BotState;
import com.example.mytelegrambot.entity.City;
import com.example.mytelegrambot.exception.CityNotFoundException;
import com.example.mytelegrambot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class DeleteCityHandler implements Handler{
    @Autowired
    CityService cityService;

    @Autowired
    CacheState cacheState;


    @Override
    public String handle(Message message, BotState currentBotState) {
        Long chatId = message.getChatId();

        City city = cityService.getCityByName(message.getText());
        if (city == null) {
            cacheState.setState(chatId, BotState.DEFAULT);
            throw new CityNotFoundException();
        }
        cityService.deleteCity(message.getText());
        cacheState.setState(chatId, BotState.DEFAULT);

        return String.format("Город с именем %s был успешно удалён",
                message.getText());
    }
}
