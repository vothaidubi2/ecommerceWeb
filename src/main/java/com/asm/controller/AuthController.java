package com.asm.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.asm.dao.UserDAO;
import com.asm.entity.Users;
import com.asm.service.AuthService;

import com.asm.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth/login")
public class AuthController {
	Date date=new Date();
	@Autowired
	AuthService as;



	@Autowired
	UserService urS;
	@Autowired
	UserDAO dao;

	@GetMapping("/form")
	public String formlogin(Users user, Model model) {
		Users users = new Users();

		model.addAttribute("form", users);
		return "auth/login";
	}
	@RequestMapping("/logout")
	public String formlogout() {
		as.setUser(null);
		System.out.println(as.getUser());
		
		return "redirect:/home";
	}
	@GetMapping("acsuccess")
	public String acsuccess( HttpSession session,HttpServletRequest request,Authentication authentication) {
		as.setUser(null);
		as.setUser(as.findByEmail(authentication.getName()));
		System.err.println();
		session.setAttribute("user", as.getUser());
	
		return "redirect:/home";
	}
	@GetMapping("/accountCheck/{email}")
	public String checkAccount(Model model,@PathVariable("email") String email) {
		as.setUser(as.findByEmail(email));
		model.addAttribute("form",as.getUser());
		return "auth/accountCRUD";
	}
	@RequestMapping("/accountCheck/success")
	public String update( @ModelAttribute("form") Users user, Errors er) {
		Users us=new Users();
		as.checkUser();
		us=as.getUser();
		us.setPassword(user.getPassword());
		us.setName(user.getName());
		us.setPhone(user.getPhone());
		as.save(us);	
		as.setUser(us);

			
		return "redirect:/home";
	}

	@RequestMapping("/create")
	public String create(Users user, Model model) {

		user.setPassword(user.getPassword());
		user.setRole(false);
		user.setStatus(true);
		user.setDate(date);
		as.save(user);
	
		Users users = new Users();

		model.addAttribute("form", users);
		return "auth/login";
	}

	@RequestMapping("/success")
	public String success(OAuth2AuthenticationToken oauth2, Model model,HttpSession session,HttpServletRequest request) {
		as.setUser(null);
		Users users = new Users();
		model.addAttribute("form", users);
		urS.loginFormOAuth(oauth2);
		users.setEmail(oauth2.getPrincipal().getAttribute("email"));
		if (as.findByEmail(users.getEmail().toString())!=null) {
			
			as.setUser(as.findByEmail(users.getEmail().toString()));
			if (!as.checkUser()) {
				request.setAttribute("user", as.getUser());
				session.setAttribute("user", as.getUser());
				return "redirect:/auth/login/accountCheck/"+as.getUser().getEmail();
			}
			session.setAttribute("user", as.getUser());
	
			return "redirect:/home";
		}else {
			users.setName(oauth2.getPrincipal().getAttribute("name"));
			users.setRole(false);
			users.setDate(date);
			users.setStatus(true);
			users.setPassword(Long.toHexString(System.currentTimeMillis()));
			as.save(users);
			as.setUser(users);
			if (!as.checkUser()) {
				session.setAttribute("user", as.getUser());
				return "redirect:/auth/login/accountCheck/"+as.getUser().getEmail();
			}
			session.setAttribute("user", as.getUser());
			return "redirect:/home";
		}
		

	}
}
