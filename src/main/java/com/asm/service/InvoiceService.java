package com.asm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asm.dao.InvoiceDAO;
import com.asm.dto.InvoiceDTO;

@Service
public class InvoiceService {
   @Autowired
   private InvoiceDAO invoiceDAO;

   public void create(InvoiceDTO dto) {
      System.out.println(dto.getAddress());
      System.out.println(dto.getPhone());
      System.out.println(dto.getPayment());
      System.out.println(dto.getProducts().get(0).getProduct());
      System.out.println(dto.getProducts().get(0).getPrice());
      System.out.println(dto.getProducts().get(0).getQuantity());
   }
}
