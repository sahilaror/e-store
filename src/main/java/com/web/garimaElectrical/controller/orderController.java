package com.web.garimaElectrical.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import com.web.garimaElectrical.helper.nameHelper;
import com.web.garimaElectrical.model.cart;
import com.web.garimaElectrical.model.orders;
import com.web.garimaElectrical.model.user;
import com.web.garimaElectrical.repository.userRepo;
import com.web.garimaElectrical.service.orderService;

import jakarta.servlet.http.HttpSession;


public class orderController {
	 @Autowired
	    private orderService orderService;
	 
	 @Autowired 
	 userRepo userRepo;

	    @GetMapping("/orderHistory")
	    public String orderHistory(Model model, Authentication authentication) {
	    	String userName=nameHelper.getname(authentication);
	    	user user=userRepo.findByEmail(userName).get();
	        List<orders> orders = orderService.getOrdersByUser(user);
	        model.addAttribute("orders", orders);
	        return "orderHistory";
	    }

	    @PostMapping("/checkout")
	    public String checkout(Model model, Authentication authentication,HttpSession httpSession) {
	    	String userName=nameHelper.getname(authentication);
	    	user user=userRepo.findByEmail(userName).get();
	        cart cart =user.getCart();
			double total1=(double)httpSession.getAttribute("finaltotal");
	        orders order = orderService.createOrderFromCart(cart,total1);
	        model.addAttribute("order", order);
	        return "orderSuccess";
	    }
}
