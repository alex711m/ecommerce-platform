package com.architecture.microservices.order_service;

public class StudentDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double originalPrice) {
        // -20% de réduction
        return originalPrice * 0.80; 
    }
}