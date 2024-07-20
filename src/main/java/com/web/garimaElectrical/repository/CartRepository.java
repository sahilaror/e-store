package com.web.garimaElectrical.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.garimaElectrical.model.cart;
import com.web.garimaElectrical.model.user;

public interface CartRepository extends JpaRepository<cart, Integer> {
    cart findByUser(user user);
}
