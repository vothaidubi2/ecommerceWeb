package com.asm.admin.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asm.service.AuthService;
import com.asm.service.SessionService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@CrossOrigin("*")
@RestController
public class AdminCheckUserController {
@Autowired
public SessionService ss;

@GetMapping("admin/api/checkuser")
public String session() {
	   String value = (String) ss.get("user");
	   System.err.println(value);
	   return value;
}
}
