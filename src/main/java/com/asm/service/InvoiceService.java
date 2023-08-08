package com.asm.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.ClientProtocolException;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.asm.dao.InvoiceDAO;
import com.asm.dao.InvoiceDetailDAO;
import com.asm.dto.InvoiceDTO;
import com.asm.entity.Invoice;
import com.asm.entity.InvoiceDetails;
import com.asm.utils.Momo;

@Service
public class InvoiceService {
	@Autowired
	private InvoiceDAO invoiceDAO;

	@Autowired
	private InvoiceDetailDAO invoiceDetailDAO;

	public Document create(InvoiceDTO dto) throws ClientProtocolException, IOException {
		Invoice invoiceData = new Invoice();
		invoiceData.setAddress(dto.getAddress());
		invoiceData.setPhone(dto.getPhone());
		invoiceData.setDatecreate(new Date());
		invoiceData.setStatus("4");

		Invoice invoiceRes = invoiceDAO.save(invoiceData);

		List<InvoiceDetails> invoiceDetails = dto.getProducts().stream().map(product -> {
			InvoiceDetails invoiceDetail = new InvoiceDetails();
			invoiceDetail.setPrice(product.getPrice());
			invoiceDetail.setQuantity(product.getQuantity());
			invoiceDetail.setProduct(product.getProduct());
			invoiceDetail.setInvoice(invoiceRes);

			return invoiceDetail;
		}).collect(Collectors.toList());

		invoiceDetailDAO.saveAll(invoiceDetails);

		Document resp = new Document();

		if (dto.getPayment().equals("momo")) {
			resp = Momo.create(dto.getTotalPrice(), "");
		}

		return resp;
	}

	public Object calculateTotalRevenueByDateBetween(String startDate, String endDate) {
		try {
			long start = changeDate(startDate);
			long end = changeDate(endDate);
			List<String> invoices = new ArrayList<>();
			if (invoiceDAO.getTotalRevenueForDateRange(-start, -end) != null) {
				invoices = invoiceDAO.getTotalRevenueForDateRange(-start, -end);
			}
			Map<String, List<String>> data = new HashMap<>();
			data.put("data", invoices);
			return data;
		} catch (Exception e) {
			return null;
		}
	}
	public Object getListOrder(String startDate, String endDate) {
		try {
			long start = changeDate(startDate);
			long end = changeDate(endDate);
			List<String> invoices = new ArrayList<>();
			if (invoiceDAO.getAllInvoiceByStatus(-start, -end) != null) {
				invoices = invoiceDAO.getAllInvoiceByStatus(-start, -end);
			}
			Map<String, List<String>> data = new HashMap<>();
			data.put("data", invoices);
			return data;
		} catch (Exception e) {
			return null;
		}
	}
	
	public ResponseEntity<List<Invoice>> getByStatus() {
		return ResponseEntity.ok(invoiceDAO.findByStatus());
	}
	public ResponseEntity<Invoice> updateStatus(@PathVariable("id") Integer id,String status) {
		Invoice existInvoice = invoiceDAO.findById(id).get();
		if(existInvoice ==null) {
			return ResponseEntity.notFound().build();
		}
		existInvoice.setStatus(status);
		return ResponseEntity.ok(invoiceDAO.save(existInvoice));
	}

	private long changeDate(String input) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
		LocalDate now = LocalDate.now();
		Date secondDate = sdf.parse(input);

		// Create a java.sql.Date object from the LocalDate
		Date firstDate = new Date(java.sql.Date.valueOf(now).getTime());

		long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
		return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}
}
