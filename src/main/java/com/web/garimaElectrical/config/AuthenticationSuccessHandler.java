package com.web.garimaElectrical.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
		
	
	 @Override
	    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
	        boolean isAdmin = authentication.getAuthorities().stream()
	                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
	        if (isAdmin) {
	            setDefaultTargetUrl("/admin/home");
	        } else {
	            setDefaultTargetUrl("/user/home");
	        }
	        super.onAuthenticationSuccess(request, response, authentication);
	    }
}

