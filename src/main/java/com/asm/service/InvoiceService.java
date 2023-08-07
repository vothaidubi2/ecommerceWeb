package com.asm.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asm.dao.InvoiceDAO;
import com.asm.dao.InvoiceDetailDAO;
import com.asm.dto.InvoiceDTO;
import com.asm.entity.Invoice;
import com.asm.entity.InvoiceDetails;
import com.asm.entity.Product;
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

      Document resp = new Document();

      if(dto.getPayment().equals("momo")) {
         resp = Momo.create(dto.getTotalPrice(), "");
      }

      return resp;
   }
}
