package com.asm.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.asm.entity.Category;
import com.asm.entity.Specification;

public interface SpecificationDAO extends JpaRepository<Specification, Integer>{
    @Query("SELECT s FROM Specification s WHERE s.keys LIKE %:key% and s.status=true")
    Page<Specification> findByKey( String key, Pageable page);
    @Query("SELECT c FROM Specification c WHERE c.status = true")
    Page<Specification> findAllWhereStatusTrue(  Pageable page);
}
