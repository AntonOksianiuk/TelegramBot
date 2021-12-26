package com.example.mytelegrambot.service;

import com.example.mytelegrambot.entity.City;
import com.example.mytelegrambot.entity.Description;
import com.example.mytelegrambot.exception.CityNotFoundException;
import com.example.mytelegrambot.repository.CityRepo;
import com.example.mytelegrambot.repository.DescriptionRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CityService {
    @Autowired
    private CityRepo cityRepo;
    @Autowired
    private DescriptionRepo descriptionRepo;

    public List<City> getAllCities() {
        return cityRepo.findAll();
    }

    public void deleteCity(String name) {
        Optional<City> city = Optional.ofNullable(cityRepo.findByName(name));
        city.ifPresent(cityRepo::delete);
    }

    public City addCity(City city) {
        log.info(String.format("add city {} with descriptions : {}"), city.getName(), city.getDescriptions());

        City newCity = new City();
        List<Description> descriptionList = city
                .getDescriptions()
                .stream()
                .map(el -> {
                    el.setCity(newCity);
                    return el;
                })
                .collect(Collectors.toList());
        newCity.setDescriptions(descriptionList);
        newCity.setName(city.getName());

        return cityRepo.save(newCity);
    }

    public void addDescriptionToCity(City city, Description description) {
        description.setCity(city);
        List<Description> descriptionList = city.getDescriptions();
        descriptionList.add(description);
        city.setDescriptions(descriptionList);
        cityRepo.save(city);
    }

    public City getCityById(Long cityId) {
        return cityRepo
                .findById(cityId)
                .orElseThrow(CityNotFoundException::new);
    }

    public City getCityByName(String name) {
        return Optional.ofNullable(
                        cityRepo.findByName(name))
                .orElseThrow(CityNotFoundException::new);
    }

    //I added a comment here
    //3 3 3
}
