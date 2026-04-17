package com.architecture.microservices.order_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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

    // NOUVEAU : Récupération des URLs depuis application.properties
    @Value("${catalogue.service.url}")
    private String catalogueServiceUrl;

    @Value("${customer.service.url}")
    private String customerServiceUrl;

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
            // 1. On interroge le customer-service avec l'URL dynamique
            CustomerDTO customer = restTemplate.getForObject(
                customerServiceUrl + "/" + order.getCustomerId(), 
                CustomerDTO.class
            );
            
            // 2. On interroge le catalogue-service avec l'URL dynamique
            ProductDTO product = restTemplate.getForObject(
                catalogueServiceUrl + "/" + order.getProductId(), 
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

    // ROUTE : Création d'une vraie commande
    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest request) {
        // 1. On donne l'ordre au catalogue de baisser le stock via une requête HTTP PUT avec l'URL dynamique
        String catalogueUrl = catalogueServiceUrl + "/" + request.productId() + "/decrease-stock?quantity=" + request.quantity();
        
        try {
            // Le RestTemplate envoie l'ordre. S'il y a une erreur (ex: plus de stock), ça passe dans le 'catch'
            restTemplate.put(catalogueUrl, null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'achat : Produit introuvable ou stock insuffisant.");
        }

        // 2. Si le catalogue a dit "OK", on sauvegarde la trace de la commande dans notre base H2
        Order newOrder = new Order(request.customerId(), request.productId(), request.quantity());
        repository.save(newOrder);

        return ResponseEntity.ok("Paiement validé ! Commande enregistrée avec succès.");
    }
}