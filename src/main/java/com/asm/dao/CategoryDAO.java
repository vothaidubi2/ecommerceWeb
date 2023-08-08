package com.asm.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.asm.entity.Category;
import com.asm.entity.Specification;

public interface CategoryDAO extends JpaRepository<Category, Integer>{
    @Query("SELECT c FROM Category c WHERE c.status = true")
    Page<Category> findAllWhereStatusTrue(  Pageable page);
}
