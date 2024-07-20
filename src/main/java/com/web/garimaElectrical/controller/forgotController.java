package com.web.garimaElectrical.controller;


import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.web.garimaElectrical.helper.emailService;
import com.web.garimaElectrical.model.user;
import com.web.garimaElectrical.validation.message;
import com.web.garimaElectrical.repository.userRepo;
import com.web.garimaElectrical.service.userService;

import jakarta.servlet.http.HttpSession;


@RestController
public class forgotController {

	@Autowired
	userRepo userRepo;
	
	@Autowired 
	userService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	emailService emailService;
	
	@GetMapping("/forgot")
	public ModelAndView forgot() {
		return new ModelAndView("forgot");
	}
	
	Random random=new Random(100000);
	
	@PostMapping("/send-otp")
	public ModelAndView otp(@RequestParam("email") String email,HttpSession session) {
		int otpGen=random.nextInt(999999);
		user user=userRepo.findByEmail(email).orElse(null);
		if (user!=null) {
		String userName=user.getName();
		String sub="GarimaElectrical-Reset Forgotten Password";
		String message=""+
				"<div style='border:1px solid #e2e2e2 padding:20px'>"+
				"<h1>"+
				"Dear "+
				userName+
				"</h1>"+
				"</n>"+
				"<h4>"+
				"We noticed that you're trying to signin with your email address but you forgot your password. No worries, we're here to help!"+
				"</h4>"+
				"</n>"+
				"<h2>"+
				"OTP for your new password is:-"+"<b>"+otpGen+
				"</h2>"+
				"</n>"+
				"<h4>"+
				"If you continue to experience issues or have any questions, please don't hesitate to reach out to our support team at garimaelectrical01792@gmail.com or call us at xxxxxxxxxx. We're here to assist you!"+
				"</h4>"+
				"</n>"+
				"<h4>"+
				"Thank you for your understanding and patience."+
				"</h4>"+
				"</n>"+
				"<h4>"+
				"Best regards,"+
				"</h4>"+
				"</n>"+
				"<h4>"+
				"GarimaElectrical Team"+
				"</h4>"+
				"<div>";

		String to=email;
//		user user1=userRepo.findByEmail(email).orElse(null);
		
			boolean flag=emailService.sendmail(sub, message, to);
			if (flag) {
				session.setAttribute("myotp",otpGen);
				session.setAttribute("email",email);
				return new ModelAndView("sendotp");
			}
			else {
				session.setAttribute("message",new message("something wrong please check your email","alert-danger"));
				return new ModelAndView("forgot");
			}
		}
		else {
			session.setAttribute("message",new message("seems like "+email+" doesn't registered with GarimaElectrical, Please Register","alert-danger"));
			return new ModelAndView("forgot");
		}
	}
	
	
	@PostMapping("/change-password")
	public ModelAndView changePass(HttpSession session,@RequestParam("otp")int userOtp) {
		int givenOtp=(int)session.getAttribute("myotp");
		
		if (givenOtp==userOtp) {
			
			return new ModelAndView("changepass");
		}
		else {
			session.setAttribute("message", new message("Wrong otp", "alert-danger"));
			return new ModelAndView("sendotp");
		}
	}
	
	@GetMapping("/new-password")
	public ModelAndView getMethodName(@RequestParam("password") String password,HttpSession session) {
		String email=(String)session.getAttribute("email");
		user user=userRepo.findByEmail(email).get();
		user.setPassword(bCryptPasswordEncoder.encode(password));
		userService.save(user);
		RedirectView rd=new RedirectView();
		rd.setUrl("/signin");
		return new ModelAndView(rd);
	}
	
	
}
