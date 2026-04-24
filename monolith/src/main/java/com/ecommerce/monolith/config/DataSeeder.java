package com.ecommerce.monolith.config;

import com.ecommerce.monolith.model.Customer;
import com.ecommerce.monolith.model.Order;
import com.ecommerce.monolith.model.Product;
import com.ecommerce.monolith.repository.CustomerRepository;
import com.ecommerce.monolith.repository.OrderRepository;
import com.ecommerce.monolith.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public DataSeeder(ProductRepository productRepository,
                      CustomerRepository customerRepository,
                      OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void run(String... args) {
        productRepository.save(new Product("Ordinateur Portable", 1200.50, 10, "https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=400"));
        productRepository.save(new Product("Livre Architecture Logicielle", 45.00, 50, "https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=400"));
        productRepository.save(new Product("Casque Audio", 89.99, 25, "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400"));
        productRepository.save(new Product("Souris Sans Fil Ergonomique", 29.99, 40, "https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=400"));
        productRepository.save(new Product("Clavier Mécanique RGB", 149.99, 15, "https://images.unsplash.com/photo-1595044426077-d36d9236d54a?w=400"));
        productRepository.save(new Product("Écran 4K 27 pouces", 399.00, 8, "https://images.unsplash.com/photo-1547082299-de196ea013d6?w=400"));
        productRepository.save(new Product("Disque Dur Externe 2To", 75.50, 20, "https://images.unsplash.com/photo-1531492746076-161ca9bcad58?w=400"));
        productRepository.save(new Product("Clé USB 128Go", 19.99, 100, "https://images.unsplash.com/photo-1618410320928-25228d811631?w=400"));
        productRepository.save(new Product("Tapis de Souris XXL", 24.90, 30, "https://images.unsplash.com/photo-1615869442320-fd02a129c77c?w=400"));
        productRepository.save(new Product("Webcam Full HD 1080p", 59.99, 18, "https://images.unsplash.com/photo-1587826080692-f439cd0b70da?w=400"));

        customerRepository.save(new Customer("Alice", "Dupont", "alice@email.com", "ETUDIANT"));
        customerRepository.save(new Customer("Bob", "Martin", "bob@email.com", "CLASSIQUE"));

        orderRepository.save(new Order(1L, 1L, 2));
        orderRepository.save(new Order(2L, 3L, 1));
    }
}
