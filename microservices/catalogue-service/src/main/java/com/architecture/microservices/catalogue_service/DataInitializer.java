package com.architecture.microservices.catalogue_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(ProductRepository repository) {
        return args -> {
            repository.save(new Product("Ordinateur Portable", 1200.50, 10));
            repository.save(new Product("Livre Architecture Logicielle", 45.00, 50));
            repository.save(new Product("Casque Audio", 89.99, 25));
        };
    }
}