package com.example.mytelegrambot.cache;

import com.example.mytelegrambot.entity.City;
import org.apache.el.stream.Optional;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CacheCity {
    private Map<Long, City> cachedCity = new HashMap<>();


    public City get(Long chatId) {
        City city = cachedCity.get(chatId);
        if (city == null){
            city = new City();
        }
        return city;
    }

    public void set(Long chatId, City city) {
        cachedCity.put(chatId, city);
    }
}
