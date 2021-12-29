package com.example.mytelegrambot.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Field can't be empty")
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "city",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<Description> descriptions = new ArrayList<>();

    public void addDescriptionToCity(String description){
        Description description1 = new Description();
        description1.setDescription(description);
        descriptions.add(description1);
    }
}
