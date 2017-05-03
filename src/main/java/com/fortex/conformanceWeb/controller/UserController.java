/**<p>Description</p>
 * @author Ivan Huo
 */
package com.fortex.conformanceWeb.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.LocaleResolver;

import com.fortex.conformanceWeb.form.LoginForm;
import com.fortex.conformanceWeb.pojo.User;
import com.fortex.conformanceWeb.service.UserService;
 
/**
 * @author Ivan Huo
 *
 */
@Controller
public class UserController  {
	private static int TRADE_LOGIN = 2;
	private static int QUOTE_LOGIN = 3;
	
	@Autowired	
	UserService userService;
	
	@Autowired
    LocaleResolver localeResolver;
		
	@GetMapping("/")
	public String login(HttpServletRequest request, HttpServletResponse response, LoginForm loginForm) {
		//localeResolver.setLocale((HttpServletRequest )request, (HttpServletResponse)response, Locale.ENGLISH);
		return "/login";
	}
		
	@PostMapping("/")
	public String checkLogin(@Valid LoginForm loginForm,BindingResult bindingResult,HttpServletRequest request){
		 if (bindingResult.hasErrors()) {
	            return "/login";
	     }
		 
		 boolean loginSuccessd = userService.loginValid(loginForm.getLoginId(),loginForm.getPassword(),request);
		 if(!loginSuccessd){
			 bindingResult.addError(new FieldError("loginInfo","illegal","Account or password illegal"));
			 return "/login";
		 }
		 User user = userService.getCurrentUser(request);
		 if(user.getLoginType() == QUOTE_LOGIN)
			 return "redirect:/track/quote";
		 else if(user.getLoginType() == TRADE_LOGIN)
			 return "redirect:/track/trade";
		 return "";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response){
		userService.logout(request);
		return "redirect:/"; 
	}
	
	
	@GetMapping("/admins")
	public String admins(LoginForm loginForm) {
		return "/admins";
	}
	
	@PostMapping("/admins")
	public String adminsCheck(@Valid LoginForm loginForm,BindingResult bindingResult,HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
	            return "/admins";
	    }
		boolean loginSuccessd= userService.adminLoginValid(loginForm.getLoginId(), loginForm.getPassword(), request);
		if(!loginSuccessd){
			 bindingResult.addError(new FieldError("loginInfo","illegal","Account or password illegal"));
			 return "/admins";
		}
		return "redirect:/admin/solutionSearch";
	}
}
