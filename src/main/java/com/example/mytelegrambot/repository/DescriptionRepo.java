package com.example.mytelegrambot.repository;

import com.example.mytelegrambot.entity.Description;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DescriptionRepo extends JpaRepository<Description, Long> {

    List<Description> findAllByCityId(Long cityId);
}
