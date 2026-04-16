package com.architecture.microservices.order_service;

public class ClassicDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double originalPrice) {
        // Pas de réduction pour le statut classique
        return originalPrice; 
    }
}