package com.asm.dao;

import com.asm.entity.Invoice;
import com.asm.entity.InvoiceDetails;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceDetailDAO extends JpaRepository<InvoiceDetails, Integer> {
	@Query("SELECT COUNT(id) FROM InvoiceDetails id")
	long count();

	@Query("SELECT SUM(id.price * id.quantity) FROM InvoiceDetails id")
	double sumTotalAmount();
	@Query(value = "select product.name,invoicedetails.price,invoicedetails.quantity from invoicedetails join product on invoicedetails.Idproduct=product.Idproduct where invoicedetails.Idinvoice=:idInvoice",nativeQuery = true)
	List<String> getInvoiceDetails(@Param("idInvoice") Integer Idinvoice);
}
