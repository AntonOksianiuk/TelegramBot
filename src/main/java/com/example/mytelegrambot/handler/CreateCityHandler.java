package com.example.mytelegrambot.handler;

import com.example.mytelegrambot.cache.CacheCity;
import com.example.mytelegrambot.cache.CacheState;
import com.example.mytelegrambot.configuration.BotState;
import com.example.mytelegrambot.entity.City;
import com.example.mytelegrambot.entity.Description;
import com.example.mytelegrambot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class CreateCityHandler implements Handler{
    @Autowired
    CacheState cacheState;
    @Autowired
    CacheCity cacheCity;
    @Autowired
    CityService cityService;


    public String handle(Message message, BotState currentBotState){
        Long chatId = message.getChatId();
        String inputMessage = message.getText();
        String replyMessage = null;
        City city = null;

        switch (currentBotState){
            case CREATE_CITY:
                //replyMessage = "Введите название города ";
                cacheState.setState(chatId, BotState.ASK_CITYNAME);
            case ASK_CITYNAME:
                city = cacheCity.get(chatId);
                city.setName(inputMessage);
                cacheCity.set(chatId, city);

                replyMessage = "Введите описание для города " + inputMessage;
                cacheState.setState(chatId, BotState.ASK_DESCRIPTION);
                break;

            case ASK_DESCRIPTION:
                city = cacheCity.get(chatId);
                city.addDescriptionToCity(inputMessage);

                cacheCity.set(chatId, city);

                cityService.addCity(city);
                replyMessage = String.format("Город %s успешно добавлен ", city.getName());

                cacheCity.set(chatId, null);

                cacheState.setState(chatId, BotState.DEFAULT);
                break;
        }

        return replyMessage;
    }

}
