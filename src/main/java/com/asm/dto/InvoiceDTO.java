package com.asm.dto;

import java.util.ArrayList;

import com.asm.entity.InvoiceDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {
   private String address;
   private String phone;
   private String payment;
   private ArrayList<InvoiceDetails> products;
}
