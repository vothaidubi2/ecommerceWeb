package com.asm.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asm.service.InvoiceService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class OrderHistoryRestController {
	@Autowired
	InvoiceService invoiceService;

	@GetMapping("/order")
	public ResponseEntity<List<String>> getOrderdetail(@RequestParam("id") Integer id) {
		return invoiceService.orderDetail(id);
	}
	@GetMapping("/order/get")
	public ResponseEntity<Map<String, String>> getOne(@RequestParam Integer id) {
		return invoiceService.getOne(id);
	}
}
