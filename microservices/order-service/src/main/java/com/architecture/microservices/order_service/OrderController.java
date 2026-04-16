package com.architecture.microservices.order_service;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    private final OrderRepository repository;
    // RestTemplate est le "navigateur web" interne de Spring pour faire des requêtes
    private final RestTemplate restTemplate = new RestTemplate(); 

    public OrderController(OrderRepository repository) {
        this.repository = repository;
    }

    // L'ancienne méthode qui renvoie juste les IDs
    @GetMapping
    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    // LA NOUVELLE MÉTHODE QUI FAIT COMMUNIQUER LES SERVICES
    @GetMapping("/full")
    public List<OrderDetails> getFullOrders() {
        List<Order> orders = repository.findAll();
        
        return orders.stream().map(order -> {
            // 1. On interroge le customer-service
            CustomerDTO customer = restTemplate.getForObject(
                "http://localhost:8082/api/customers/" + order.getCustomerId(), 
                CustomerDTO.class
            );
            
            // 2. On interroge le catalogue-service
            ProductDTO product = restTemplate.getForObject(
                "http://localhost:8081/api/products/" + order.getProductId(), 
                ProductDTO.class
            );

            // --- 3. APPLICATION DU DESIGN PATTERN STRATEGY ---
            DiscountStrategy strategy;
            
            // On sélectionne dynamiquement l'algorithme selon le statut
            if ("ETUDIANT".equalsIgnoreCase(customer.status())) {
                strategy = new StudentDiscountStrategy();
            } else {
                strategy = new ClassicDiscountStrategy();
            }

            // On calcule le prix de base (prix unitaire * quantité)
            double basePrice = product.price() * order.getQuantity();
            
            // L'algorithme calcule le prix final de manière transparente
            double finalPrice = strategy.applyDiscount(basePrice);
            // -------------------------------------------------
            
            // 4. On assemble le tout (en ajoutant finalPrice à la fin)
            return new OrderDetails(order.getId(), order.getQuantity(), customer, product, finalPrice);
            
        }).collect(Collectors.toList());
    }
}