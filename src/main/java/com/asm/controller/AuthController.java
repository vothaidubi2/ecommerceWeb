package com.asm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asm.dao.UserDAO;
import com.asm.entity.Users;
import com.asm.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/auth/login")
public class AuthController {
	Users userUpdate;
	@Bean
	public BCryptPasswordEncoder psE() {
		return new BCryptPasswordEncoder();
	}

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

	@GetMapping("/accountCheck")
	public String checkAccount(Model model) {
//		System.err.println(userUpdate.getEmail()+"abc");
//		System.err.println( userUpdate.getEmail());
		
		model.addAttribute("form", userUpdate);
		return "auth/accountCRUD";
	}
	@RequestMapping("/accountCheck/success")
	public String update(@Validated @ModelAttribute("form") Users user, Errors er) {
	Users updateUser=	dao.findByEmail(userUpdate.getEmail());
	updateUser.setName(user.getName());
	updateUser.setPhone(user.getPhone());
	updateUser.setPassword(user.getPassword());
	
	
dao.save(updateUser);		
return "redirect:/home";
	}


	@RequestMapping("/create")
	public String create(Users user, Model model) {
		user.setPassword(psE().encode(user.getPassword()));
		user.setRole(false);
		dao.save(user);
		Users users = new Users();
		model.addAttribute("form", users);
		return "auth/login";
	}

	@RequestMapping("/success")
	public String success(OAuth2AuthenticationToken oauth2, Model model) {
		Users users = new Users();
		model.addAttribute("form", users);
		users.setEmail(oauth2.getPrincipal().getAttribute("email"));
		users.setName(oauth2.getPrincipal().getAttribute("name"));
		users.setRole(true);
		users.setPassword(psE().encode(Long.toHexString(System.currentTimeMillis())));
		userUpdate=dao.findByEmail(users.getEmail());
		if (dao.findByEmail(users.getEmail().toString()) != null) {
			System.err.println(users.getName());
			if (!chekUser(dao.findByEmail(users.getEmail()))) {
				return "redirect:/auth/login/accountCheck";
			}
			return "redirect:/home";
		}else {
			dao.save(users);
			if (!chekUser(dao.findByEmail(users.getEmail()))) {
				return "redirect:/auth/login/accountCheck";
			}
		}
		urS.loginFormOAuth(oauth2);
		return "redirect:/home";
	}
	public boolean chekUser(Users user) {
		if (user.getName() == null || user.getPhone() == null) {
			return false;
		}
		return true;
	}

		System.out.println(users);
		if (dao.findByEmail(users.getEmail().toString()) != null) {
			urS.loginFormOAuth(oauth2);
			return "redirect:/home";
		}
		dao.save(users);
		urS.loginFormOAuth(oauth2);
		return "redirect:/home";
	}

	@GetMapping("/policyprivacy")
	public String policyandprivacy() {
		return "auth/policyandprivacy";
	}

}
