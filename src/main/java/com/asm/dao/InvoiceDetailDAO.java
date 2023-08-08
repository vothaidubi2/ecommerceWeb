package com.asm.dao;

import com.asm.entity.Invoice;
import com.asm.entity.InvoiceDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvoiceDetailDAO extends JpaRepository<InvoiceDetails, Integer> {
	@Query("SELECT COUNT(id) FROM InvoiceDetails id")
	long count();

	@Query("SELECT SUM(id.price * id.quantity) FROM InvoiceDetails id")
	double sumTotalAmount();
}
