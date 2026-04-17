package com.architecture.microservices.order_service;

// Un simple DTO pour recevoir les données du formulaire d'achat
public record OrderRequest(Long customerId, Long productId, int quantity) {}