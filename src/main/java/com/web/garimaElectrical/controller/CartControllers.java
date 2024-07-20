package com.web.garimaElectrical.controller;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.web.garimaElectrical.helper.nameHelper;
import com.web.garimaElectrical.model.cart;
import com.web.garimaElectrical.model.user;
import com.web.garimaElectrical.repository.userRepo;
import com.web.garimaElectrical.validation.message;

import jakarta.servlet.http.HttpSession;
@RestController
public class CartControllers {
	 @Autowired
	    private userRepo userRepository;

    @Autowired
    private com.web.garimaElectrical.service.cartService cartService;
    
    String couponCode="n";
    double finaltotal;

    @GetMapping("/addToCart/{id}")
    public ModelAndView addItemToCart(@PathVariable("id") Integer id,Authentication authentication,Model model,HttpSession httpSession) {
    	String username=nameHelper.getname(authentication);
    	httpSession.setAttribute("productid",id);
    	int quantity=1;
    	user user = userRepository.findByEmail(username).get();
        cart cart = user.getCart();
        model.addAttribute("count", cart.getItems().size());
        cartService.addItemToCart(username, id, quantity);
        RedirectView rd=new RedirectView();
		rd.setUrl("/user/shop");
		return new ModelAndView(rd);
    }

    @GetMapping("/qty")
    public ModelAndView qty(@RequestParam("qty")int qty, HttpSession session) {
    	session.setAttribute("qty",qty);
    	RedirectView rd=new RedirectView();
		rd.setUrl("/cart");
		return new ModelAndView(rd);	
    }
    
    
    @GetMapping("/cart")
    public ModelAndView viewCart(Model model,Authentication authentication , HttpSession session) {
    	System.out.println(couponCode);
    	String username=nameHelper.getname(authentication);
    	int tqty;
    	
    	if (session.getAttribute("qty")==null) {
			tqty=1;
		}
    	else {
			 tqty =(int)session.getAttribute("qty");
		}
    	model.addAttribute("tqty",tqty);
    	user user = userRepository.findByEmail(username).get();
        cart cart = user.getCart();
        if (cart.getItems().size()==0) {
			return new ModelAndView("user/cartEmpty");
		}
        double total=cart.getItems().stream().mapToDouble(item -> item.getProduct().getPrice() *tqty).sum();
        if (total>=900) {
			model.addAttribute("fee","Free");
		}
        else {
        	model.addAttribute("fee",30);
        }
        
        if (couponCode.equalsIgnoreCase(user.getCoupon()) && total>=900 || couponCode.equalsIgnoreCase(user.getCoupon1()) && total>=900 || couponCode.equalsIgnoreCase(user.getCoupon2()) && total>=900 ) {
        	
        	
        	
			if (couponCode.equalsIgnoreCase(user.getCoupon())) {
				model.addAttribute("disc",100);
				finaltotal=total-100.0;
				model.addAttribute("gtotal",total-100.0);
				model.addAttribute("save",total*0.33+100);
				
				 model.addAttribute("couponName",couponCode);
			}
			else if (couponCode.equalsIgnoreCase(user.getCoupon1())) {
				double disc10=total*0.1;
				model.addAttribute("disc",disc10);
				finaltotal=total-disc10;
				model.addAttribute("gtotal",total-disc10);
				model.addAttribute("save",total*0.33+disc10);
				 model.addAttribute("couponName",couponCode);
			}
			else if(couponCode.equalsIgnoreCase("nocode")){
				session.setAttribute("disc",0);
				finaltotal=total-0;
				model.addAttribute("gtotal",total-0);
				model.addAttribute("save",total*0.33+0);
				
			}
			else {
				double disc20=total*0.2;
				model.addAttribute("disc",disc20);
				finaltotal=total-disc20;
				model.addAttribute("gtotal",total-disc20);
				model.addAttribute("save",total*0.33+disc20);
			}
		}
      
        else {
			model.addAttribute("disc",0);
			double del;
			if (total>=900) {
				del=0;
			}
			
			else {
				del=30.0;
			}
			 if (couponCode.equalsIgnoreCase(user.getCoupon()) && total<900 || couponCode.equalsIgnoreCase(user.getCoupon1()) && total<900 || couponCode.equalsIgnoreCase(user.getCoupon2()) && total<900 ) {
		    	session.setAttribute("message",new message("Your total is less than 900","alert-danger"));
				}
			finaltotal=total+del;
			model.addAttribute("gtotal",total+del);
			model.addAttribute("save",total*0.33);
		}
        model.addAttribute("count", cart.getItems().size());
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return new ModelAndView("user/cart");
    }

    @GetMapping("/coupon")
    public ModelAndView coupons(@RequestParam("discount-code")String coupon,Authentication authentication,Model model,HttpSession session) {
    	int tqty =(int)session.getAttribute("qty");
    	String username=nameHelper.getname(authentication);
        user user = userRepository.findByEmail(username).get();
        cart cart = user.getCart();
        couponCode=coupon;
    	double total=cart.getItems().stream().mapToDouble(item -> item.getProduct().getPrice() * tqty).sum();
    	if(coupon.equalsIgnoreCase(user.getCoupon()) && total>=900 || coupon.equalsIgnoreCase(user.getCoupon1()) && total>=900 || coupon.equalsIgnoreCase(user.getCoupon2()) && total>=900 ) {
    		model.addAttribute("disc",100);
    		session.setAttribute("message",new message("Coupon applied "+coupon,"alert-success"));
    	}
    	else if (coupon.equalsIgnoreCase(user.getCoupon()) && total<900 || coupon.equalsIgnoreCase(user.getCoupon1()) && total<900 || coupon.equalsIgnoreCase(user.getCoupon2()) && total<900 ) {
    		session.setAttribute("message",new message("Your total is less than 900","alert-danger"));
		}
    	else {
    		session.setAttribute("message",new message("Wrong/Invalid coupon","alert-danger"));
    	}
    
    	System.out.println("code isisisiiss"+coupon);
    	RedirectView rd=new RedirectView();
    	rd.setUrl("/cart");
    	return new ModelAndView(rd);
    }
    
    @GetMapping("/cart/removeItem/{id}")
    public ModelAndView removeItemFromCart(@PathVariable Integer id, Authentication authentication,Model model) {
    	String username=nameHelper.getname(authentication);
    	user user=userRepository.findByEmail(username).get();
    	cart cart=user.getCart();
    	model.addAttribute("zero",0);
    	model.addAttribute("cartCount",cart.getItems().size());
        cartService.removeItemFromCart(username, id);
        RedirectView rd=new RedirectView();
		rd.setUrl("/cart");
		return new ModelAndView(rd);
    }
    
	@GetMapping("checkout")
	public ModelAndView checkout(Model model,HttpSession session,Authentication authentication) {
		String username=nameHelper.getname(authentication);
    	user user=userRepository.findByEmail(username).get();
    	cart cart=user.getCart();
    	model.addAttribute("count",cart.getItems().size());
		session.setAttribute("finaltotal",finaltotal);
		model.addAttribute("ft", finaltotal);
		session.setAttribute("cc",couponCode);
		return new ModelAndView("user/checkOut");
	}
}
