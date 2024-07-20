//package com.web.garimaElectrical.controller;
//
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.view.RedirectView;
//import com.web.garimaElectrical.global.globalVariable;
//import com.web.garimaElectrical.model.productModel;
//import com.web.garimaElectrical.service.productService;
//
//import jakarta.servlet.http.HttpSession;
//
//import java.util.Iterator;
//
//import org.slf4j.*;
//
//@Controller
//public class cartController {
//
//	Logger logger=LoggerFactory.getLogger(cartController.class);
//	
//	@Autowired
//	productService productService;
//	
//	@GetMapping("/addToCart/{id}")
//	public ModelAndView addToCart(@PathVariable("id") Integer id,Model model) {
//		model.addAttribute("cartCount", globalVariable.cart.size());
//		globalVariable.cart.add(productService.update(id).get());
//		RedirectView rd=new RedirectView();
//		rd.setUrl("/user/shop");
//		return new ModelAndView(rd);
//	}
//	
//	@GetMapping("/cart")
//	public ModelAndView cart(Model model,HttpSession httpSession) {
//		model.addAttribute("cartCount", globalVariable.cart.size());
//		model.addAttribute("total",globalVariable.cart.stream().mapToDouble(productModel::getPrice).sum());
//		double total11=(double) globalVariable.cart.stream().mapToDouble(productModel::getPrice).sum();
//		httpSession.setAttribute("total11",total11);
//		model.addAttribute("cart",globalVariable.cart);
//		return new ModelAndView("user/cart");
//	}
//
//	@GetMapping("/cart/removeItem/{id}")
//	public ModelAndView removeFromCart(@PathVariable("id") Integer id) {
//		 Iterator<productModel> iterator = globalVariable.cart.iterator();
//	        while (iterator.hasNext()) {
//	            productModel product = iterator.next();
//	            if (product.getPid().equals(id)) {
//	                iterator.remove();
//	                break;
//	            }
//	        }
//		RedirectView rd=new RedirectView();
//		rd.setUrl("/cart");
//		return new ModelAndView(rd);
//	}
//	
//	@GetMapping("checkout")
//	public ModelAndView checkout(Model model,HttpSession httpSession) {
//		model.addAttribute("total",globalVariable.cart.stream().mapToDouble(productModel::getPrice).sum());
//		double total=globalVariable.cart.stream().mapToDouble(productModel::getPrice).sum();
//		httpSession.setAttribute("ttl", total);
//		return new ModelAndView("user/checkOut");
//	}
//}
