package com.web.garimaElectrical.config;

import java.io.IOException;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.web.garimaElectrical.model.user;
import com.web.garimaElectrical.repository.userRepo;
import com.web.garimaElectrical.service.userService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class authenticationSuccess implements AuthenticationSuccessHandler{

	Logger logger=LoggerFactory.getLogger(authenticationSuccess.class);
	
	user user1=new user();
	
	@Autowired
	userService userService;
	
	@Autowired
	userRepo userRepo;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		var oauth2authenticationToken=(OAuth2AuthenticationToken) authentication;
		String authenticationSuccess =oauth2authenticationToken.getAuthorizedClientRegistrationId();
		
		//checking weather it is a google,linkedin,facebook and github login
		logger.info(authenticationSuccess);
		
		DefaultOAuth2User oAuth2User=(DefaultOAuth2User)authentication.getPrincipal();
		
		//checking all attributed of client
//		oAuth2User.getAttributes().forEach((key,value)->{
//			logger.info(key+""+value);
//		});
		
		user1.setId(UUID.randomUUID().hashCode());
		user1.setRole("USER");
		user1.setPassword("password");
		user1.setCoupon("GENEW100");
		user1.setCoupon1("GE10%");
		user1.setCoupon2("GE20%");
		
		if (authenticationSuccess.equalsIgnoreCase("google")) {
			user1.setName(oAuth2User.getAttribute("name").toString());
			user1.setEmail(oAuth2User.getAttribute("email").toString());
			user1.setImageUrl(oAuth2User.getAttribute("picture").toString());
			user1.setProvider("google");
			
		}
		else if (authenticationSuccess.equalsIgnoreCase("github")) {	
			String email=oAuth2User.getAttribute("email") !=null ?
					oAuth2User.getAttribute("email").toString() : oAuth2User.getAttribute("login").toString()+"@gmail.com";
			String picture=oAuth2User.getAttribute("avatar_url").toString();
			String name=oAuth2User.getAttribute("login").toString();
			String providerUserId=oAuth2User.getName();
			logger.info("provider id is:-"+providerUserId);
			user1.setName(name);
			user1.setEmail(email);
			user1.setImageUrl(picture);
			user1.setProvider("github");
			
		}
		else if (authenticationSuccess.equalsIgnoreCase("facebook")) {
			
		}
		else {
			
		}
	
		user user2=userRepo.findByEmail(user1.getEmail()).orElse(null);
		if (user2==null) {
		userService.save(user1);
		}
			new DefaultRedirectStrategy().sendRedirect(request, response,"/user/home");	
		
	}

}
