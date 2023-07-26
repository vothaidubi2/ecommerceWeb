package com.asm.admincontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ProductController {
	@GetMapping("/product")
	public String view(Model model) {
		model.addAttribute("headtitle", "Product Management");
		return "admin/product";
	}
}
