package com.asm.dao;

import com.asm.entity.Invoice;
import com.asm.entity.InvoiceDetails;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceDetailDAO extends JpaRepository<InvoiceDetails, Integer> {
   
}
