package com.example.mytelegrambot.repository;

import com.example.mytelegrambot.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepo extends JpaRepository<City, Long> {
    City findByName(String name);
}
