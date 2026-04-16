package com.architecture.microservices.order_service;

public record OrderDetails(Long orderId, int quantity, CustomerDTO customer, ProductDTO product) {}