package com.asm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.asm.dao.CategoryDAO;
import com.asm.dao.SpecificationDAO;
import com.asm.entity.Category;
import com.asm.entity.Specification;

@Service
public class CategoryService {
	@Autowired
	CategoryDAO dao;
	public Page<Category> findAll(Pageable page) {
		return  dao.findAll(page);
		
	}

	public Category deleteById(Category sp) {
		sp.setStatus(false);
		dao.save(sp);
		return sp;
	}
	public Category save(Category sp) {
		sp.setStatus(true);
		dao.save(sp);
		return sp;
	}
	public Page<Category> findAllStatusTrue(Pageable page) {
		return dao.findAllWhereStatusTrue(page);
		
	}


}
