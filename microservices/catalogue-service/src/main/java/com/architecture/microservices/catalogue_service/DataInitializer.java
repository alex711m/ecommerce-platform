package com.architecture.microservices.catalogue_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(ProductRepository repository) {
        return args -> {
            repository.save(new Product("Ordinateur Portable", 1200.50, 10, "https://images.unsplash.com/photo-1496181133206-80ce9b88a853?auto=format&fit=crop&w=600&h=600"));
            repository.save(new Product("Livre Architecture Logicielle", 45.00, 50, "https://images.unsplash.com/photo-1594305342010-1f62e803490f?auto=format&fit=crop&w=600&h=600"));
            repository.save(new Product("Casque Audio", 89.99, 25, "https://images.unsplash.com/photo-1618366712010-f4ae9c647dcb?auto=format&fit=crop&w=600&h=600"));
            repository.save(new Product("Souris Sans Fil Ergonomique", 29.99, 100, "https://images.unsplash.com/photo-1613141411244-0e4ac259d217?auto=format&fit=crop&w=600&h=600"));
            repository.save(new Product("Clavier Mécanique RGB", 149.99, 30, "https://images.unsplash.com/photo-1618384887929-16ec33fab9ef?auto=format&fit=crop&w=600&h=600"));
            repository.save(new Product("Écran 4K 27 pouces", 399.00, 15, "https://images.unsplash.com/photo-1585792180666-f7347c490ee2?auto=format&fit=crop&w=600&h=600"));
            repository.save(new Product("Disque Dur Externe 2To", 75.50, 40, "https://plus.unsplash.com/premium_photo-1721133221361-4f2b2af3b6fe?auto=format&fit=crop&w=600&h=600"));
            repository.save(new Product("Clé USB 128Go", 19.99, 200, "https://images.unsplash.com/photo-1587145820098-23e484e69816?auto=format&fit=crop&w=600&h=600"));
            repository.save(new Product("Tapis de Souris XXL", 24.90, 80, "https://images.unsplash.com/photo-1631098983935-5363b8e50edb?auto=format&fit=crop&w=600&h=600"));
            repository.save(new Product("Webcam Full HD 1080p", 59.99, 45, "https://images.unsplash.com/photo-1623949556303-b0d17d198863?auto=format&fit=crop&w=600&h=600"));
        };
    }
}