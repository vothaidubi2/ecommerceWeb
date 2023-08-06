package com.asm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.asm.dao.UserDAO;
import com.asm.entity.Specification;
import com.asm.entity.Users;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class AuthService {
	@Autowired
	SessionService ss;
	Users user = new Users();
	@Autowired
	UserDAO dao;
	@Bean
	public BCryptPasswordEncoder psE() {
		return new BCryptPasswordEncoder();
	}
	public Users save(Users users) {
		System.err.println(users.getPassword());
		users.setPassword(psE().encode(users.getPassword()));
		dao.save(users);
		return users;
	}

	public Users findByEmail(String email) {
		if (dao.findByEmail(email) == null) {
			return null;
		} else {
			return dao.findByEmail(email);
		}
	}

	public Users getUser() {
		return this.user;
	}
	public Users deleteUser(Users userss) {
		userss.setStatus(false);
		dao.save(userss);
		return userss;
	}
	public boolean checkUser() {
		if (user.getRole() == null || user.getStatus() == null || user.getDate() == null || user.getEmail() == null
				|| user.getPassword() == null || user.getId() == null || user.getPhone() == null
				|| user.getName() == null) {
			return false;
		}
		return true;
	}

	public void setUser(Users users) {
		this.user = users;

	}

	public void saveSession() {
		ss.set("user", getUser().getName());
		
	}
	public Page<Users> findAll(Pageable page){
		return dao.findAllWhereStatusTrue(page);
	}
	public Page<Users> findTableByEmail(String key, Pageable page) {
		return dao.findTableByEmail(key, page);
}
	






}
