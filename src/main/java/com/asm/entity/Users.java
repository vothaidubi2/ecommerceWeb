package com.asm.entity;
// Generated Jul 21, 2023, 11:15:56 AM by Hibernate Tools 4.3.6.Final

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Users generated by hbm2java
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Iduser")
	private Integer id;

	@Column(name = "email")
	private String email;
	@Column(name = "name")
	private String name;

	@Column(name = "password")
	private String password;
	@Column(name = "phone")
	private String phone;

	@Column(name = "role")
	private Boolean role;

	@Column(name = "datecreated")
	private Date date;
	private Boolean status;
	@JsonIgnore
	@OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
	private List<Invoice> invoices;
}
