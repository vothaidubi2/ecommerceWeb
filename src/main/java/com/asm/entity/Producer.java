package com.asm.entity;
// Generated Jul 21, 2023, 11:15:56 AM by Hibernate Tools 4.3.6.Final

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Producer generated by hbm2java
 */
@Entity
@Table(name = "producer", schema = "dbo", catalog = "DBAsmJv6")
public class Producer implements java.io.Serializable {

	private String idProducer;
	private Serializable name;
	private Set<Product> products = new HashSet<Product>(0);

	public Producer() {
	}

	public Producer(String idProducer) {
		this.idProducer = idProducer;
	}

	public Producer(String idProducer, Serializable name, Set<Product> products) {
		this.idProducer = idProducer;
		this.name = name;
		this.products = products;
	}

	@Id

	@Column(name = "idProducer", unique = true, nullable = false, length = 10)
	public String getIdProducer() {
		return this.idProducer;
	}

	public void setIdProducer(String idProducer) {
		this.idProducer = idProducer;
	}

	@Column(name = "name")
	public Serializable getName() {
		return this.name;
	}

	public void setName(Serializable name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "producer")
	public Set<Product> getProducts() {
		return this.products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

}
