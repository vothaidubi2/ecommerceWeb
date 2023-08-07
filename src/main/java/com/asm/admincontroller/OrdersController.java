package com.asm.admincontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrdersController {
	@GetMapping("/admin/orders")
	public String view() {
		return "admin/orders";
	}
}
