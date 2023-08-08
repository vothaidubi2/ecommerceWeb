package com.asm.admincontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
	@GetMapping("/admin/user")
	public String category() {
		return "admin/user";
	}
}
