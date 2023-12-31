package com.asm.entity;
// Generated Jul 21, 2023, 11:15:56 AM by Hibernate Tools 4.3.6.Final

import java.io.Serializable;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Product generated by hbm2java
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Idproduct")
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "category")
	private Category category;
	@ManyToOne
	@JoinColumn(name = "producer")
	private Producer producer;

	private String name;

	private Double price;

	private Integer quantity;

	private Boolean status;
	
	private String description;
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<SpecificationDetails> specificationDetailses;
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<InvoiceDetails> invoiceDetailses;
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<Image> images;
}
