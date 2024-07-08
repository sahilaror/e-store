package com.web.garimaElectrical.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.garimaElectrical.model.user;
import com.web.garimaElectrical.repository.userRepo;

@Service
public class userService {

	@Autowired
	userRepo userRepo;
	
	public void save(user user) {
		userRepo.save(user);
	}

	
}
