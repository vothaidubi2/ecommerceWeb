package com.asm.entity;
// Generated Jul 21, 2023, 11:15:56 AM by Hibernate Tools 4.3.6.Final

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Invoice generated by hbm2java
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "invoice", schema = "dbo", catalog = "DBAsmJv6")
public class Invoice implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Idinvoice")
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "Iduser")
	private Users users;
	@Column(name = "phone")
	private String phone;
	@Column(name = "address")
	private Serializable address;
	@Temporal(TemporalType.DATE)
	@Column(name = "dateCreate")
	private Date dateCreate;
	@Column(name = "status")
	private Boolean status;
	@JsonIgnore
	@OneToMany( mappedBy = "invoice")
	private List<InvoiceDetails> invoiceDetailses;
}
