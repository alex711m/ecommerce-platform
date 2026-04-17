package com.architecture.microservices.order_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner initData(OrderRepository repository) {
        return args -> {
            // Le client 1 (Alice) achète 2 fois le produit 1 (Ordi)
            repository.save(new Order(1L, 1L, 2));
            // Le client 2 (Bob) achète 1 fois le produit 3 (Casque)
            repository.save(new Order(2L, 3L, 1));
        };
    }
}