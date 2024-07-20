package com.web.garimaElectrical.helper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class nameHelper {

	public static String getname(Authentication authentication) {
		
		String username="";
		
		
		// if user login with github , google
		if (authentication instanceof OAuth2AuthenticationToken) {
			var oauth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
			String clientName=oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
			var oauth2user=(OAuth2User)authentication.getPrincipal();
			//with google
			if (clientName.equalsIgnoreCase("google")) {
				username=oauth2user.getAttribute("email").toString();
			}
			else if (clientName.equalsIgnoreCase("github")) {
				username=oauth2user.getAttribute("email")!=null ?
						oauth2user.getAttribute("email").toString() : oauth2user.getAttribute("login").toString()+"@gmail.com";
			}
				return username;
			
		}
		else if(authentication instanceof UsernamePasswordAuthenticationToken) {
			username= authentication.getName();
		  return username;
	}
		else {
			return "";
		}
}
}
