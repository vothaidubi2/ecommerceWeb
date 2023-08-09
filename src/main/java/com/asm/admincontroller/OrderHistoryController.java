package com.asm.admincontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asm.service.InvoiceService;

@Controller
public class OrderHistoryController {
	@Autowired
	InvoiceService invoiceService;
	
	@GetMapping("/admin/order")
	public String view(@RequestParam String id) {
		return "admin/order-history";
	}
}
