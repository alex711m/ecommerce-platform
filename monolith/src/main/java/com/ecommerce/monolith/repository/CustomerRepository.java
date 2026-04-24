package com.ecommerce.monolith.repository;

import com.ecommerce.monolith.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {}
