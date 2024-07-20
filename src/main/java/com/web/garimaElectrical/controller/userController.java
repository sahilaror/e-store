package com.web.garimaElectrical.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.web.garimaElectrical.helper.nameHelper;
import com.web.garimaElectrical.repository.userRepo;
import com.web.garimaElectrical.service.categoryService;
import com.web.garimaElectrical.service.orderService;
import com.web.garimaElectrical.service.productService;

import jakarta.servlet.http.HttpSession;

import com.web.garimaElectrical.model.cart;
import com.web.garimaElectrical.model.orders;
import com.web.garimaElectrical.model.user;


@Controller
@RequestMapping("/user")
public class userController {
	
	Logger logger=LoggerFactory.getLogger(userController.class);
	
	@Autowired
    private orderService orderService;
	
	@Autowired
	categoryService categoryService;
	
	@Autowired
	productService productService;
	
	@Autowired
	userRepo userRepo;
	
	@GetMapping("/home")
	public ModelAndView user() {
		return new ModelAndView("user/home");
	}
	@GetMapping("/shop")
	public ModelAndView shop(Model model,Authentication authentication) {
		String username=nameHelper.getname(authentication);
		user user = userRepo.findByEmail(username).get();
        cart cart = user.getCart();
        model.addAttribute("count", cart.getItems().size());
		model.addAttribute("categories", categoryService.show());
		model.addAttribute("products", productService.show());
		return new ModelAndView("user/shop");
	}
	@GetMapping("/shop/category/{id}")
	public ModelAndView shopCategory(@PathVariable("id") Integer id,Model model,Authentication authentication) {
		String username=nameHelper.getname(authentication);
		user user = userRepo.findByEmail(username).get();
        cart cart = user.getCart();
        model.addAttribute("count", cart.getItems().size());
		model.addAttribute("categories",categoryService.show());
		model.addAttribute("products",productService.getAllProductByCategory(id));
		return new ModelAndView("user/shop");
	}
	@GetMapping("/shop/viewproduct/{id}")
	public ModelAndView viewProduct(@PathVariable("id") Integer id,Model model,Authentication authentication) {
		String username=nameHelper.getname(authentication);
		user user = userRepo.findByEmail(username).get();
        cart cart = user.getCart();
        model.addAttribute("count", cart.getItems().size());
		model.addAttribute("product",productService.update(id).get());
		return new ModelAndView("user/viewProduct");
	}
	@GetMapping("/termsAndConditions")
	public ModelAndView termsAndConditions() {
		return new ModelAndView("user/termsAndConditions");
	}
	@GetMapping("/privatePolicy")
	public ModelAndView privatePolicy() {
		return new ModelAndView("user/privatePolicy");
	}
	@GetMapping("/refundPolicy")
	public ModelAndView refundPolicy() {
		return new ModelAndView("user/refundPolicy");
	}
	@GetMapping("/account")
	public ModelAndView accout(Authentication authentication,Model model,HttpSession httpSession)
	{
		String userName=nameHelper.getname(authentication);
		user user=userRepo.findByEmail(userName).get();
		String url=user.getImageUrl();
		model.addAttribute("userImg",url);
		model.addAttribute("userInfo", userName);
		cart cart1=new cart();
		Integer oi=cart1.getId();
		System.out.println(oi);
		if (oi==null) {
			return new ModelAndView("user/account");
		}
		else {
			        cart cart =user.getCart();
					double total1=(double)httpSession.getAttribute("finaltotal");
			        orders order = orderService.createOrderFromCart(cart,total1);
			        model.addAttribute("order", order);
			        return new ModelAndView("user/account1");
			    }	
		}
}
