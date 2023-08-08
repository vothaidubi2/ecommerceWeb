package com.asm.admincontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
<<<<<<<< HEAD:src/main/java/com/asm/admincontroller/SpecificationController.java
@RequestMapping("/admin")
public class SpecificationController {
	@RequestMapping("/specification")
	public String view(Model model) {
		return "admin/specification";
========
public class StatisticalController {
	@GetMapping("/admin/statistical")
	public String view() {
		return "admin/statistical";
>>>>>>>> master:src/main/java/com/asm/admincontroller/StatisticalController.java
	}
}
