package com.esprit.microservice.author1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class Author1Application {

    public static void main(String[] args) {
        SpringApplication.run(Author1Application.class, args);
    }

}
