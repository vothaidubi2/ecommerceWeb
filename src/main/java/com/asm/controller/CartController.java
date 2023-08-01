package com.asm.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.asm.dto.InvoiceDTO;
import com.asm.entity.Invoice;
import com.asm.entity.InvoiceDetails;
import com.asm.service.InvoiceService;

@Controller
public class CartController {
   @Autowired
   InvoiceService invoiceService;

	@GetMapping("/cart")
	public String cart() {
		return "cart";
	}
	@GetMapping("/checkout")
	public String checkout() {
		return "/checkout";
	}

   @PostMapping("/checkout")
   public String createInvoice(@RequestBody InvoiceDTO dto) {
      invoiceService.create(dto);
      
      return "/checkout";
   }
}
