package com.asm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
	@GetMapping("/cart")
	public String cart() {
		return "cart";
	}
	@GetMapping("/checkout")
	public String checkout() {
		return "/checkout";
	}
}
