package com.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asm.entity.Invoice;

public interface InvoiceDAO extends JpaRepository<Invoice, Integer> {
   
}
