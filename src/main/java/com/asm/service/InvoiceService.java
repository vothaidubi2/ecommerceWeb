package com.asm.service;

import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asm.dao.InvoiceDAO;
import com.asm.dao.InvoiceDetailDAO;
import com.asm.dto.InvoiceDTO;
import com.asm.entity.Invoice;
import com.asm.entity.InvoiceDetails;
import com.asm.entity.Product;

@Service
public class InvoiceService {
   @Autowired
   private InvoiceDAO invoiceDAO;

   @Autowired
   private InvoiceDetailDAO invoiceDetailDAO;

   public void create(InvoiceDTO dto) {
      Invoice invoiceData = new Invoice();
      invoiceData.setAddress(dto.getAddress());
      invoiceData.setPhone(dto.getPhone());
      invoiceData.setStatus(false);
      
      Invoice invoiceRes = invoiceDAO.save(invoiceData);

      List<InvoiceDetails> invoiceDetails = dto.getProducts().stream().map(
         product -> {
            InvoiceDetails invoiceDetail = new InvoiceDetails();
            invoiceDetail.setPrice(product.getPrice());
            invoiceDetail.setQuantity(product.getQuantity());
            invoiceDetail.setProduct(product.getProduct());
            invoiceDetail.setInvoice(invoiceRes);

            return invoiceDetail;
         }
      ).collect(Collectors.toList());

      invoiceDetailDAO.saveAll(invoiceDetails);

      // System.out.println(dto.getAddress());
      // System.out.println(dto.getPhone());
      // System.out.println(dto.getPayment());
      // System.out.println(dto.getProducts().get(0).getProduct());
      // System.out.println(dto.getProducts().get(0).getPrice());
      // System.out.println(dto.getProducts().get(0).getQuantity());
   }
}
