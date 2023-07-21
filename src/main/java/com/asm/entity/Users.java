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
 * Users generated by hbm2java
 */
@Entity
@Table(name = "users", schema = "dbo", catalog = "DBAsmJv6")
public class Users implements java.io.Serializable {

	private String idUser;
	private Serializable email;
	private Serializable name;
	private Serializable password;
	private Serializable phone;
	private Boolean role;
	private Set<Invoice> invoices = new HashSet<Invoice>(0);

	public Users() {
	}

	public Users(String idUser) {
		this.idUser = idUser;
	}

	public Users(String idUser, Serializable email, Serializable name, Serializable password, Serializable phone,
			Boolean role, Set<Invoice> invoices) {
		this.idUser = idUser;
		this.email = email;
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.role = role;
		this.invoices = invoices;
	}

	@Id

	@Column(name = "idUser", unique = true, nullable = false, length = 10)
	public String getIdUser() {
		return this.idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	@Column(name = "email")
	public Serializable getEmail() {
		return this.email;
	}

	public void setEmail(Serializable email) {
		this.email = email;
	}

	@Column(name = "name")
	public Serializable getName() {
		return this.name;
	}

	public void setName(Serializable name) {
		this.name = name;
	}

	@Column(name = "password")
	public Serializable getPassword() {
		return this.password;
	}

	public void setPassword(Serializable password) {
		this.password = password;
	}

	@Column(name = "phone")
	public Serializable getPhone() {
		return this.phone;
	}

	public void setPhone(Serializable phone) {
		this.phone = phone;
	}

	@Column(name = "role")
	public Boolean getRole() {
		return this.role;
	}

	public void setRole(Boolean role) {
		this.role = role;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<Invoice> getInvoices() {
		return this.invoices;
	}

	public void setInvoices(Set<Invoice> invoices) {
		this.invoices = invoices;
	}

}
