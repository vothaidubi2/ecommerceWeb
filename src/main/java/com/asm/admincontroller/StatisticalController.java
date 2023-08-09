package com.asm.admincontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticalController {
	@GetMapping("/admin/statistical")
	public String view() {
		return "admin/statistical";
	}
}
