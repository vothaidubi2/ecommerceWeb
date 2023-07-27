package com.asm.admincontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class StatisticalController {
	@GetMapping("/statistical")
	public String view(Model model) {
		model.addAttribute("headtitle", "Statistical");
		return "admin/statistical";
	}
}
