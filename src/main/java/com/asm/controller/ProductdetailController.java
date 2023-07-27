package com.asm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductdetailController {
	@GetMapping("/productdetail")
	public String view() {
		return "productdetail";
	}
}
