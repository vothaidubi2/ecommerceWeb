package com.asm.entity;
// Generated Jul 21, 2023, 11:15:56 AM by Hibernate Tools 4.3.6.Final

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SpecificationDetails generated by hbm2java
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "specificationdetails")
public class SpecificationDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Idspecificationdetails")
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "Idproduct")
	private Product product;
	@ManyToOne
	@JoinColumn(name = "Idspecification")
	private Specification specification;
}
