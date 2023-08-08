package com.asm.admin.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asm.entity.Invoice;
import com.asm.service.InvoiceService;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin/api")
public class AdminOrdersRestController {
	@Autowired
	InvoiceService invoiceService;
	
	@GetMapping("/orders")
	public ResponseEntity<List<Invoice>> view() {
		return invoiceService.getByStatus();
	}
	@PutMapping("/orders/{id}")
	public ResponseEntity<Invoice> updateStatus(@PathVariable("id") Integer id,@RequestBody String status) {
		return invoiceService.updateStatus(id,status);
	}
}
