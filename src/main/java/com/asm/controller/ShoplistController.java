package com.asm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShoplistController {
	@GetMapping("/shoplist")
	public String shoplist() {
		return "shoplist";
	}
}
