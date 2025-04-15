package com.esprit.microservice.genre;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GenreApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenreApplication.class, args);
    }
    @Autowired
    private GenreRepository genreRepository;

    @Bean
    ApplicationRunner init() {
        return (args) -> {
        genreRepository.save(new Genre("Drama","abcde eaze df frzvv rez ere.",10));
        genreRepository.save(new Genre("Horror","abcde eaze df frzvv rez ere.",95));
        genreRepository.save(new Genre("Action","abcde eaze df frzvv rez ere.",90));
        };
    }

}
