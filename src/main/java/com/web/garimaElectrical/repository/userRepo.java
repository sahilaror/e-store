package com.web.garimaElectrical.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.web.garimaElectrical.model.user;

@Repository
public interface userRepo extends JpaRepository<user,Integer> {
	@Query("select u from user u where u.email = :email")
	public user getUserByUserName(@Param("email") String username);
	
	 Optional<user> findByEmail(String email);
}
