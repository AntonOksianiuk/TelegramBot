package com.example.mytelegrambot.facade;

import com.example.mytelegrambot.entity.City;
import com.example.mytelegrambot.entity.Description;
import com.example.mytelegrambot.exception.CityNotFoundException;
import com.example.mytelegrambot.exception.DescriptionNotFoundException;
import com.example.mytelegrambot.service.CityService;
import com.example.mytelegrambot.service.DescriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
@Slf4j
public class TelegramFacade {
    private CityService cityService;
    private DescriptionService descriptionService;

    @Autowired
    public TelegramFacade(CityService cityService, DescriptionService descriptionService) {
        this.cityService = cityService;
        this.descriptionService = descriptionService;
    }

    public SendMessage handleUpdate(Update update) {
        SendMessage replyMessage = null;
        Message message = update.getMessage();

        if (message != null && message.hasText()) {
            log.info("New message from user: {}, chatId: {}, with text: {}",
                    message.getFrom().getId(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        } else {
            replyMessage = new SendMessage(update.getMessage().getChatId().toString(), "The message is empty");
        }
        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {
        String inputMessage = message.getText();
        long userId = message.getFrom().getId();
        String replyMessage = null;

        switch (inputMessage) {
            case "/start":
                replyMessage = "Введите название города, а я выведу информацию о нём";
                break;

            case "Показать города":
                replyMessage = "Доступны описания к следующим городам : \n" +
                        cityService.getAllCities().stream()
                                .map(el -> el.getName())
                                .reduce((accumulator, element) -> element + " ")
                                .get();
                break;
            case "Добавить город":


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

        return new SendMessage(message.getChatId().toString(), replyMessage);
    }
}

//This is a new comment from github
//This is a second comment from github
