package com.asm.entity;
// Generated Jul 21, 2023, 11:15:56 AM by Hibernate Tools 4.3.6.Final

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Image generated by hbm2java
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image")
public class Image {

	@Id
	@Column(name = "Idimage")
	private String idImage;
	@ManyToOne
	@JoinColumn(name = "Idproduct")
	private Product product;

	@Column(name = "URL")
	private String url;

}
