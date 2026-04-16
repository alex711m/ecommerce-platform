package com.architecture.microservices.catalogue_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(ProductRepository repository) {
        return args -> {
            // 10 produits du catalogue
            repository.save(new Product("Ordinateur Portable", 1200.50, 10));
            repository.save(new Product("Livre Architecture Logicielle", 45.00, 50));
            repository.save(new Product("Casque Audio", 89.99, 25));
            repository.save(new Product("Souris Sans Fil Ergonomique", 29.99, 100));
            repository.save(new Product("Clavier Mécanique RGB", 149.99, 30));
            repository.save(new Product("Écran 4K 27 pouces", 399.00, 15));
            repository.save(new Product("Disque Dur Externe 2To", 75.50, 40));
            repository.save(new Product("Clé USB 128Go", 19.99, 200));
            repository.save(new Product("Tapis de Souris XXL", 24.90, 80));
            repository.save(new Product("Webcam Full HD 1080p", 59.99, 45));
        };
    }
}