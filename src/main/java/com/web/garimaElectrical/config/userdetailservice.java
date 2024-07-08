package com.web.garimaElectrical.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.web.garimaElectrical.model.user;
import com.web.garimaElectrical.repository.userRepo;

public class userdetailservice implements UserDetailsService{

	@Autowired
	private userRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		user user=userRepo.getUserByUserName(username);
		if (user==null) {
			throw new UsernameNotFoundException("could not found user");
		}
		userdetail ud=new userdetail(user);
		return ud;
	}

}
