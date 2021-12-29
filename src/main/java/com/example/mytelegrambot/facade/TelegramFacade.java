package com.example.mytelegrambot.facade;

import com.example.mytelegrambot.cache.CacheCity;
import com.example.mytelegrambot.cache.CacheState;
import com.example.mytelegrambot.configuration.BotState;
import com.example.mytelegrambot.exception.CityAlreadyExistException;
import com.example.mytelegrambot.handler.CreateCityHandler;
import com.example.mytelegrambot.handler.DefaultHandler;
import com.example.mytelegrambot.service.CityService;
import com.example.mytelegrambot.service.DescriptionService;
import com.example.mytelegrambot.util.BotStateUtil;
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
    private CacheState cacheState;
    @Autowired
    private CreateCityHandler createCityHandler;
    @Autowired
    private DefaultHandler defaultHandler;
    @Autowired
    private CacheCity cacheCity;


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
        Long chatId = message.getChatId();
        String inputMessage = message.getText();
        long userId = message.getFrom().getId();
        String replyMessage = null;

        log.info("Запрос на закэшированные стейты");

        BotState currentBotState = cacheState.getState(chatId);
        try {
            switch (BotStateUtil.getMainBotState(currentBotState)) {
                case CREATE_CITY:
                    replyMessage = createCityHandler.handle(message, currentBotState);
                    break;
                case DEFAULT:
                    replyMessage = defaultHandler.handle(message, currentBotState);
                    break;
            }


        } catch (CityAlreadyExistException e){
            replyMessage = "Город уже существует!!!";
            cacheState.setState(chatId, BotState.DEFAULT);
            cacheCity.set(chatId, null);
        }

        return new SendMessage(message.getChatId().toString(), replyMessage);
    }
}
