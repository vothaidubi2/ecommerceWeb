package com.asm.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.asm.entity.Invoice;

public interface InvoiceDAO extends JpaRepository<Invoice, Integer> {
	@Query("SELECT COUNT(i) FROM Invoice i")
	long count();

	@Query(value = "SELECT invoice.dateCreate as 'date',sum(price * quantity) as 'revenue' FROM invoice left join invoicedetails on invoice.Idinvoice = invoicedetails.Idinvoice where invoice.dateCreate between CONVERT(date,DATEADD(day, :startDate,  GETDATE())) and CONVERT(date,DATEADD(day, :endDate,  GETDATE())) group by invoice.dateCreate", nativeQuery = true)
	List<String> getTotalRevenueForDateRange(@Param("startDate") long startDate, @Param("endDate") long endDate);

	@Query(value = "select invoice.Idinvoice,invoice.Iduser,invoice.phone,invoice.address,invoice.dateCreate,sum(price*quantity) as 'totalPrice' from invoice left join invoicedetails on invoice.Idinvoice = invoicedetails.Idinvoicedetails where status <> '0' and invoice.dateCreate between CONVERT(date,DATEADD(day, :startDate,  GETDATE())) and CONVERT(date,DATEADD(day, :endDate,  GETDATE())) group by invoice.Idinvoice,invoice.Iduser,invoice.phone,invoice.address,invoice.dateCreate,price,quantity", nativeQuery = true)
	List<String> getAllInvoiceByStatus(@Param("startDate") long startDate, @Param("endDate") long endDate);
	
	@Query(value = "select * from invoice where status <> '0'", nativeQuery = true)
	List<Invoice> findByStatus();
}
