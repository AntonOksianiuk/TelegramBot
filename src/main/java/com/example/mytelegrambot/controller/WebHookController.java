package com.example.mytelegrambot.controller;

import com.example.mytelegrambot.MyTravelTelegramBot;
import com.example.mytelegrambot.entity.City;
import com.example.mytelegrambot.entity.Description;
import com.example.mytelegrambot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.validation.Valid;
import java.util.List;

@RestController
public class WebHookController {
    private final MyTravelTelegramBot telegramBot;

    @Autowired
    private CityService cityService;

    @Autowired
    public WebHookController(MyTravelTelegramBot myTravelTelegramBot) {
        telegramBot = myTravelTelegramBot;
    }

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return telegramBot.onWebhookUpdateReceived(update);
    }

    @GetMapping("/cities")
    public ResponseEntity<List<City>> getAllCities() {
        return new ResponseEntity<>(cityService.getAllCities(), HttpStatus.OK);
    }

    @GetMapping("/cities/{cityId}")
    public ResponseEntity<City> getCityByName(@PathVariable("cityId") Long cityId) {
        return new ResponseEntity<>(cityService.getCityById(cityId), HttpStatus.OK);
    }

    @PostMapping("/cities/{city}/description")
    public ResponseEntity<City> addDescriptionToCity(@Valid @PathVariable("city") City city,
                                                     @Valid @RequestBody Description description,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        cityService.addDescriptionToCity(city, description);
        return new ResponseEntity<>(cityService.getCityById(city.getId()), HttpStatus.OK);
    }

    @PostMapping("/cities")
    public ResponseEntity<City> addCity(@Valid @RequestBody City city) {
        cityService.addCity(city);
        return new ResponseEntity<>(cityService.getCityByName(city.getName()), HttpStatus.OK);
    }

}
