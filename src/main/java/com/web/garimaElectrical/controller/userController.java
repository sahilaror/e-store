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
import com.web.garimaElectrical.global.globalVariable;
import com.web.garimaElectrical.helper.nameHelper;
import com.web.garimaElectrical.repository.userRepo;
import com.web.garimaElectrical.service.categoryService;
import com.web.garimaElectrical.service.productService;
import com.web.garimaElectrical.model.user;


@Controller
@RequestMapping("/user")
public class userController {
	
	Logger logger=LoggerFactory.getLogger(userController.class);
	
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
	public ModelAndView shop(Model model) {
		model.addAttribute("cartCount", globalVariable.cart.size());
		model.addAttribute("categories", categoryService.show());
		model.addAttribute("products", productService.show());
		return new ModelAndView("user/shop");
	}
	@GetMapping("/shop/category/{id}")
	public ModelAndView shopCategory(@PathVariable("id") Integer id,Model model) {
		model.addAttribute("cartCount", globalVariable.cart.size());
		model.addAttribute("categories",categoryService.show());
		model.addAttribute("products",productService.getAllProductByCategory(id));
		return new ModelAndView("user/shop");
	}
	@GetMapping("/shop/viewproduct/{id}")
	public ModelAndView viewProduct(@PathVariable("id") Integer id,Model model) {
		model.addAttribute("cartCount", globalVariable.cart.size());
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
	public ModelAndView accout(Authentication authentication,Model model)
	{
		String userName=nameHelper.getname(authentication);
		user user=userRepo.findByEmail(userName).get();
		String url=user.getImageUrl();
		model.addAttribute("userImg",url);
		System.out.println(url);
		model.addAttribute("userInfo", user);
		if (globalVariable.cart.size()==0) {
			return new ModelAndView("user/account");
		}
		else {
			return new ModelAndView("user/account1");	
		}
	}
}
