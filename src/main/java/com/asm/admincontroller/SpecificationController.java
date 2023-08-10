package com.asm.admincontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class SpecificationController {
	@RequestMapping("/specification")
	public String view(Model model) {
		return "admin/specification";

}
}
