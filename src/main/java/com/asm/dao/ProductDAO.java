package com.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asm.entity.Product;

public interface ProductDAO extends JpaRepository<Product, String>{

}
