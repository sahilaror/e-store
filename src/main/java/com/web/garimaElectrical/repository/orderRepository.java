package com.web.garimaElectrical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.web.garimaElectrical.model.orders;
import com.web.garimaElectrical.model.user;

public interface orderRepository extends JpaRepository<orders, Integer> {
	 List<orders> findByUser(user user);
}
