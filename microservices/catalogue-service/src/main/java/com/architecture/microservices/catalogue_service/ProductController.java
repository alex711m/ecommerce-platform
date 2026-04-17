package com.architecture.microservices.catalogue_service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Produit non trouvé"));
    }

    // ROUTE : Pour diminuer le stock après un achat
    @PutMapping("/{id}/decrease-stock")
    public ResponseEntity<String> decreaseStock(@PathVariable Long id, @RequestParam int quantity) {
        Product product = repository.findById(id).orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        
        if (product.getStock() >= quantity) {
            product.setStock(product.getStock() - quantity); // On baisse le stock
            repository.save(product); // On sauvegarde en base de données
            return ResponseEntity.ok("Stock mis à jour avec succès");
        } else {
            return ResponseEntity.badRequest().body("Erreur : Stock insuffisant");
        }
    }
}