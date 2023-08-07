package com.asm.admin.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asm.service.InvoiceService;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin/api/statistics")
public class StatisticalRestController {
	@Autowired
	InvoiceService invoiceService;;

	@GetMapping("/revenue")
	public Object getRevenueStatistics(@RequestParam String startDate, @RequestParam String endDate) {
		return invoiceService.calculateTotalRevenueByDateBetween(startDate, endDate);
	}
	@GetMapping("/listorder")
	public Object getList(@RequestParam String startDate, @RequestParam String endDate) {
		return invoiceService.getListOrder(startDate, endDate);
	}
}
