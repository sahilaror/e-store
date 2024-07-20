package com.web.garimaElectrical.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.web.garimaElectrical.global.globalVariable;
import com.web.garimaElectrical.model.user;
import com.web.garimaElectrical.service.userService;
import com.web.garimaElectrical.validation.message;

import jakarta.servlet.http.HttpSession;

@RestController
public class loginController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	userService userService;

	@GetMapping("/signup")
	public ModelAndView login(Model model) {
		model.addAttribute("login",new user());
		return new ModelAndView("signup");
	}
	
	@PostMapping("/postlogin")
	public ModelAndView postligin(@ModelAttribute("login") user user,HttpSession session,Model model) {
		try {
			user.setImageUrl("https://us.123rf.com/450wm/timbrk/timbrk1403/timbrk140300061/26566069-lightning-gold-symbol-on-a-black-background.jpg");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setProvider("SELF");
			user.setCoupon("GENEW100");
			user.setCoupon1("GE10%");
			user.setCoupon2("GE20%");
			user.setRole("USER");
			userService.save(user);
			model.addAttribute("login",new user());
			session.setAttribute("message", new message("Successfully Register!","alert-success" ));
			
			RedirectView rd=new RedirectView();
			rd.setUrl("/signup");
			return new ModelAndView(rd);
		}  
		catch (Exception e) {
			model.addAttribute("login", user);
			session.setAttribute("message", new message("Something Went Wrong!"+e.getMessage(),"alert-danger"));

			RedirectView rd=new RedirectView();
			rd.setUrl("/signup");
			return new ModelAndView(rd);
		}
		
	}
	@GetMapping("/home")
	public ModelAndView home() {
		return new ModelAndView("home");
	}
	@GetMapping("/about")
	public ModelAndView about() {
		return new ModelAndView("user/about");
	}
	@GetMapping("/signin")
	public ModelAndView signin() {
		globalVariable.cart.clear();
		return new ModelAndView("signin");
	}
	
	
}
