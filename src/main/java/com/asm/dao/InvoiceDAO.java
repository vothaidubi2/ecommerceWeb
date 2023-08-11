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

	@Query(value = "SELECT invoice.dateCreate as 'date',sum(price * quantity) as 'revenue' FROM invoice join invoicedetails on invoice.Idinvoice = invoicedetails.Idinvoice where status='1' and invoice.dateCreate between CONVERT(date,DATEADD(day, :startDate,  GETDATE())) and CONVERT(date,DATEADD(day, :endDate,  GETDATE())) group by invoice.dateCreate order by datecreate asc", nativeQuery = true)
	List<String> getTotalRevenueForDateRange(@Param("startDate") long startDate, @Param("endDate") long endDate);

	@Query(value = "SELECT invoice.Idinvoice, invoice.Iduser, invoice.phone, invoice.address, invoice.dateCreate, total.totalPrice AS 'totalPrice' FROM invoice LEFT JOIN (SELECT Idinvoice, SUM(price * quantity) AS 'totalPrice' FROM invoicedetails GROUP BY Idinvoice) AS total ON invoice.Idinvoice = total.Idinvoice WHERE invoice.status = '1' AND invoice.dateCreate BETWEEN CONVERT(date, DATEADD(day, :startDate, GETDATE())) AND CONVERT(date, DATEADD(day, :endDate, GETDATE())) ORDER BY invoice.dateCreate ASC", nativeQuery = true)
	List<String> getAllInvoiceByStatus(@Param("startDate") long startDate, @Param("endDate") long endDate);
	
	@Query(value = "select * from invoice where status <> '0' and status=:status order by datecreate desc", nativeQuery = true)
	List<Invoice> findByStatus(@Param("status") String status);
	
	@Query(value = "SELECT invoice.Idinvoice, users.name, invoice.phone, invoice.address, invoice.dateCreate, invoice.status,total.totalPrice AS 'totalPrice' FROM invoice left join users on invoice.Iduser=users.Iduser LEFT JOIN (SELECT Idinvoice, SUM(price * quantity) AS 'totalPrice' FROM invoicedetails GROUP BY Idinvoice) AS total ON invoice.Idinvoice = total.Idinvoice WHERE invoice.Idinvoice=:id", nativeQuery = true)
	String getOneById(@Param("id") Integer Id);
	
	@Query(value = "select * from invoice where invoice.Iduser=:idUser order by datecreate desc", nativeQuery = true)
	List<Invoice> findByUserId(@Param("idUser") Integer id);
}
