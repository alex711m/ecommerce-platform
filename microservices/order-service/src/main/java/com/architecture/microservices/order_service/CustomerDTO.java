package com.architecture.microservices.order_service;

public record CustomerDTO(Long id, String firstName, String lastName, String email, String status) {}