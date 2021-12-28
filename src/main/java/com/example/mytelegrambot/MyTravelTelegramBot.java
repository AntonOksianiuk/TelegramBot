package com.example.mytelegrambot;

import com.example.mytelegrambot.facade.TelegramFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class MyTravelTelegramBot extends TelegramWebhookBot {
    private String webHookPath;
    private String botUserName;
    private String botToken;
    private ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();

    @Autowired
    private TelegramFacade telegramFacade;

    public MyTravelTelegramBot(DefaultBotOptions options) {
        super(options);
        initializeKeyboardByDefault();
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        SendMessage replyMessage = telegramFacade.handleUpdate(update);
        replyMessage.setReplyMarkup(replyKeyboard);
        return replyMessage;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }

    public void setWebHookPath(String webHookPath) {
        this.webHookPath = webHookPath;
    }

    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

    private void initializeKeyboardByDefault(){
        KeyboardRow keyboardRow1 = new KeyboardRow();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        keyboardRow1.add("Показать города");
        keyboardRow1.add("Добавить город");
        keyboardRow1.add("Удалить город");

        keyboardRows.add(keyboardRow1);

        replyKeyboard.setKeyboard(keyboardRows);
        replyKeyboard.setSelective(true);
        replyKeyboard.setOneTimeKeyboard(false);
        replyKeyboard.setResizeKeyboard(true);
    }
}
