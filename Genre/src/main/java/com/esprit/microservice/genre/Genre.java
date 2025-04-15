package com.esprit.microservice.genre;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer idGenre;

    String name;
    String description;
    Integer popularity;

    public Genre(String name, String description, int someValue) {
        this.name = name;
        this.description = description;
        this.popularity = someValue;
    }

}
