package com.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asm.entity.Category;

public interface CategoryDAO extends JpaRepository<Category, Integer>{

}
