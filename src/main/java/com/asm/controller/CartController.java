package com.asm.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
		System.out.println("cảt");
		return "cart";
	}

	@GetMapping("/checkout")
	public String checkout() {
		return "/checkout";
	}

	@PostMapping("/checkout")
	public ResponseEntity<Document> createInvoice(@RequestBody InvoiceDTO dto)
			throws ClientProtocolException, IOException {
		Document resp = invoiceService.create(dto);

		System.out.println("RESPONE TO CLIENT!");
		System.out.println(resp);

		return ResponseEntity.ok(resp);
	}

	@GetMapping("/checkout-success/transaction/successful")
	public String sucess() {
		return "/checkout-success";
	}
}
