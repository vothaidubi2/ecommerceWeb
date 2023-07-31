package com.asm.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asm.dao.UserDAO;
import com.asm.entity.Users;
import com.asm.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auth/login")
@RequiredArgsConstructor
public class AuthController {

	private final UserService urS;
	private final UserDAO dao;
	private final PasswordEncoder passwordEncoder;

	@GetMapping("/form")
	public String formlogin(Users user, Model model) {
		Users users = new Users();

		model.addAttribute("form", users);
		return "auth/login";
	}

	@RequestMapping("/create")
	public String create(Users user, Model model) {

		user.setPassword(passwordEncoder.encode(user.getPassword()));
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
		users.setPassword(passwordEncoder.encode(Long.toHexString(System.currentTimeMillis())));
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
