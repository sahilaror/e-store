package com.web.garimaElectrical.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.web.garimaElectrical.model.user;

@SuppressWarnings("serial")
public class userdetail implements UserDetails{
	
	
	private user users;

	public userdetail(com.web.garimaElectrical.model.user user) {
		super();
		this.users = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return List.of(() -> users.getRole());
	}
	
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return users.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return users.getEmail();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
