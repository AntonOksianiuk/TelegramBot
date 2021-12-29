package com.example.mytelegrambot.handler;

import com.example.mytelegrambot.cache.CacheState;
import com.example.mytelegrambot.configuration.BotState;
import com.example.mytelegrambot.entity.City;
import com.example.mytelegrambot.entity.Description;
import com.example.mytelegrambot.exception.CityNotFoundException;
import com.example.mytelegrambot.exception.DescriptionNotFoundException;
import com.example.mytelegrambot.service.CityService;
import com.example.mytelegrambot.service.DescriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DefaultHandler {
    @Autowired
    CityService cityService;
    @Autowired
    CacheState cacheState;
    @Autowired
    DescriptionService descriptionService;

    public String handle(Message message, BotState currentBotState){
        Long chatId = message.getChatId();
        String inputMessage = message.getText();
        String replyMessage = null;

        switch (inputMessage) {
            case "/start":
                replyMessage = "Введите название города, а я выведу информацию о нём";
                break;

            case "Показать города":
                replyMessage = "Доступны описания к следующим городам : \n";

                List<String> stringList = cityService.getAllCities().stream()
                        .map(el -> el.getName()).collect(Collectors.toList());

                for (String s : stringList){
                    replyMessage += "\n" + s;
                }

                break;
            case "Добавить город":
                cacheState.setState(chatId, BotState.CREATE_CITY);
                replyMessage = "Введите имя города";
                break;

            default:
                try {
                    City recievedCity = cityService.getCityByName(inputMessage);
                    Description recievedDescription = descriptionService.getRandomDescriptionForCity(recievedCity);

                    replyMessage = recievedDescription.getDescription();
                } catch (CityNotFoundException e) {
                    replyMessage = "Ничего не знаю про город " + inputMessage + " ..." +
                            "\nЧтобы увидеть список доступных городов введите команду \"Показать города\"";
                } catch (DescriptionNotFoundException e) {
                    replyMessage = "Для города " + inputMessage + " нет описания";
                }
                break;
        }

        return replyMessage;
    }
}
