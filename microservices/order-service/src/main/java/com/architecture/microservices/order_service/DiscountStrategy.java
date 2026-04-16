package com.architecture.microservices.order_service;

public interface DiscountStrategy {
    double applyDiscount(double originalPrice);
}