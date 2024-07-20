package com.web.garimaElectrical.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.garimaElectrical.model.cartItems;

public interface CartItemRepository extends JpaRepository<cartItems, Integer> {
}