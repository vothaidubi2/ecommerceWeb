package com.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asm.entity.Specification;

public interface SpecificationDAO extends JpaRepository<Specification, Integer>{

}
