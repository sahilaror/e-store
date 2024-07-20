package com.web.garimaElectrical.controller;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.web.garimaElectrical.helper.nameHelper;
import com.web.garimaElectrical.model.user;
import com.web.garimaElectrical.repository.CartRepository;
import com.web.garimaElectrical.repository.userRepo;
import com.web.garimaElectrical.service.paypalService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class paypalController {

	@Autowired
	private  paypalService paypalService;
	
	@Autowired
	CartRepository cartRepository;
	

	
	@Autowired
	userRepo userRepo;
	
	
	@GetMapping("/payment")
	public ModelAndView home() {
	
		return new ModelAndView("payment");
	}
	
	@PostMapping("/payment/create")
	public RedirectView createPayment(HttpSession httpSession) {	
		double total1=(double) httpSession.getAttribute("finaltotal");
		double conversionINRtoUSA=total1/83.55;
		System.out.println(conversionINRtoUSA);
		try {
			String cancleUrl ="http://localhost:8080/payment/cancel";
			String successUrl="http://localhost:8080/payment/success";
			Payment payment=paypalService.createPayment(conversionINRtoUSA, "USD", "paypal", "sale", "payment description", successUrl, cancleUrl);
			
			for(Links links : payment.getLinks()) {
				if (links.getRel().equals("approval_url")) {
					return new RedirectView(links.getHref());
				}
			}
		} catch (PayPalRESTException e) {
			// TODO: handle exception
			
		
		}
		return new RedirectView("/payment/error");
	}
	
	@GetMapping("/payment/success")
	public ModelAndView paymentSuccess(HttpSession httpSession,Model model,Authentication authentication) {
		String cc=(String) httpSession.getAttribute("cc");
		String username=nameHelper.getname(authentication);
		user user=userRepo.findByEmail(username).get();
		if(user!=null) {
		if (user.getCoupon() != null && user.getCoupon().equalsIgnoreCase(cc)) {
			user.setCoupon(null);
		}
		else if (user.getCoupon1() != null && user.getCoupon1().equalsIgnoreCase(cc)) {
			user.setCoupon1(null);
		}
		else if (user.getCoupon2() != null && user.getCoupon2().equalsIgnoreCase(cc)) {
			user.setCoupon2(null);
		}
		else {
			new RuntimeException("coupon not found");
		}
		}
		userRepo.save(user);
		Random random=new Random(10000000);
		int ran=random.nextInt(99999999);
		String rand="GE01R"+ran;
		model.addAttribute("ran", rand);
		model.addAttribute("uname",user.getName());
		double total1=(double)httpSession.getAttribute("finaltotal");
		double conversionINRtoUSA=total1/83.55;
		DecimalFormat df = new DecimalFormat("#.##"); // Format to 2 decimal places
	    String formatted = df.format(conversionINRtoUSA);
		model.addAttribute("ct",formatted);
		LocalDate ld=LocalDate.now();
		int day=ld.getDayOfMonth();
		Month month=ld.getMonth();
		int year=ld.getYear();
		model.addAttribute("day",day);
		model.addAttribute("month",month);
		model.addAttribute("year",year);
		LocalTime lt=LocalTime.now();
		int hours=lt.getHour();
		int min=lt.getMinute();
		model.addAttribute("hours",hours);
		model.addAttribute("min", min);
		try {
		//	http://localhost:8080/payment/success?paymentId=PAYID-M2IHDZA2DL62151U1414923F&token=EC-4H122817ND372554K&PayerID=Q6ZPFBZHV6EEY
			Payment payment=paypalService.executePayment("PAYID-M2IHDZA2DL62151U1414923F&token=EC-4H122817ND372554K","Q6ZPFBZHV6EEY");
			if (payment.getState().equals("approved")) {
				return new ModelAndView("paymentsuccess");	
			}
		} catch (PayPalRESTException e) {
			

		}
		
		
		System.out.println("ggs");
		return new ModelAndView("paymentSuccess");
	}
	
	@GetMapping("/payment/cancel")
	public ModelAndView paymentCancle() {
		return new ModelAndView("paymentCancel");
	}
	
	
	@GetMapping("/payment/error")
	public ModelAndView paymenterror() {
		return new ModelAndView("paymentError");
	}
}
