package com.web.garimaElectrical.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class myConfig  {
	
	@Autowired
	authenticationSuccess handler;
	
	@Bean
	 UserDetailsService getUserdetailsService() {
		return new userdetailservice();
	}
	@Bean
	 BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public DaoAuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider dao=new DaoAuthenticationProvider();
		dao.setUserDetailsService(this.getUserdetailsService());
		dao.setPasswordEncoder(this.getBCryptPasswordEncoder());
		return dao;
	}
	@Bean
	 SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorized ->{
			authorized.requestMatchers("/signup","/home","/about","/postlogin","/css/**","/js/**","/productImages/**","/bannerImg/**","/image/**","/signin","/forgot","/send-otp","/change-password","/new-password","/cart","/user/home").permitAll();
			authorized.requestMatchers("/admin/**").hasAuthority("ADMIN");
//			authorized.requestMatchers("/admin/**").hasRole("ADMIN");
//			authorized.requestMatchers("/user/**").hasRole("USER");
			authorized.anyRequest().authenticated();
		});
		http.formLogin(form ->
			form.loginPage("/signin")
			.loginProcessingUrl("/dologin")
			.successHandler(new AuthenticationSuccessHandler())
		); 
		
		http.oauth2Login(form->{
			form.loginPage("/signin")
			.successHandler(handler);
		});

		http.csrf(c ->c.disable());
		return http.build();
	}
}
