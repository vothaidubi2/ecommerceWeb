package com.asm.admincontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asm.dao.ProductDAO;
import com.asm.dao.SpecificationDAO;
import com.asm.entity.Specification;

@Controller
@RequestMapping("/admin")
public class SpecificationController {
	@RequestMapping("/specification")
	public String view(Model model) {
		return "admin/specification";
	}
}
