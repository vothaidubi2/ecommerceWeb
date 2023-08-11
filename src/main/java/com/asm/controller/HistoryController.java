package com.asm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.asm.service.AuthService;
import com.asm.service.InvoiceService;
import com.asm.service.UserService;

@Controller
public class HistoryController {
	@Autowired
	InvoiceService invoiceService;
	@Autowired
	AuthService auth;
	
	@GetMapping("/cart/orders")
	public String orders(Model model) {
		model.addAttribute("list", invoiceService.getByUserId(auth.getUser().getId()));
		return "orders";
	}
	
	@GetMapping("/cart/history")
	public String history(@RequestParam Integer id) {
		return "order-history";
	}
}
