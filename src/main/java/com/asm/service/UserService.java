package com.asm.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import com.asm.dao.UserDAO;
import com.asm.entity.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserDAO dao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// load user form database
		Users user = dao.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		return new CustomUserDetals(user);
	}

	public void loginFormOAuth(OAuth2AuthenticationToken oauth2) {
		String username = oauth2.getPrincipal().getName();
		String email = oauth2.getPrincipal().getAttribute("email");
		UserDetails user = User.withUsername(email).password("123").roles("USER").build();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

}
