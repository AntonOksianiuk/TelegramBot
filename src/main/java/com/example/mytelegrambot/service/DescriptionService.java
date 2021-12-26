package com.example.mytelegrambot.service;

import com.example.mytelegrambot.entity.City;
import com.example.mytelegrambot.entity.Description;
import com.example.mytelegrambot.exception.DescriptionNotFoundException;
import com.example.mytelegrambot.repository.DescriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class DescriptionService {
    DescriptionRepo descriptionRepo;

    @Autowired
    public DescriptionService(DescriptionRepo descriptionRepo) {
        this.descriptionRepo = descriptionRepo;
    }

    public Description getRandomDescriptionForCity(City city){
        Long cityId = city.getId();
        List<Description> descriptionList = descriptionRepo.findAllByCityId(cityId);

        return Optional.ofNullable(
                descriptionList.get(new Random()
                        .nextInt(descriptionList.size())))
                .orElseThrow(DescriptionNotFoundException::new);

    }
}
