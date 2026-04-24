package com.ecommerce.monolith.controller;

import com.ecommerce.monolith.dto.OrderDetails;
import com.ecommerce.monolith.dto.OrderRequest;
import com.ecommerce.monolith.model.Order;
import com.ecommerce.monolith.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAll() {
        return orderService.findAll();
    }

    @GetMapping("/full")
    public List<OrderDetails> getAllFull() {
        return orderService.findAllFull();
    }

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest request) {
        try {
            orderService.placeOrder(request);
            return ResponseEntity.ok("Paiement validé ! Commande enregistrée avec succès.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'achat : " + e.getMessage());
        }
    }
}
