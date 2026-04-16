package com.architecture.microservices.customer_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner initData(CustomerRepository repository) {
        return args -> {
            // Alice est ETUDIANTE (-20%)
            repository.save(new Customer("Alice", "Dupont", "alice@email.com", "ETUDIANT"));
            // Bob est CLASSIQUE (0%)
            repository.save(new Customer("Bob", "Martin", "bob@email.com", "CLASSIQUE"));
        };
    }
}