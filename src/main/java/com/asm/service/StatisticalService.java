package com.asm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.asm.dao.SpecificationDAO;
import com.asm.entity.Specification;

@Service
public class StatisticalService {
	@Autowired
	SpecificationDAO dao;
	public Page<Specification> findAll(Pageable page) {
		System.err.println(dao.findAllWhereStatusTrue(page).getSize());
		return dao.findAllWhereStatusTrue(page);
		
	}

	public Specification deleteById(Specification sp) {
		sp.setStatus(false);
		dao.save(sp);
		return sp;
	}
	public Specification save(Specification sp) {
		sp.setStatus(true);
		dao.save(sp);
		return sp;
	}
	public Page<Specification> findAllStatusTrue(Pageable page) {

		return dao.findAllWhereStatusTrue(page);
		
	}

	public Page<Specification> findByKey(String key, Pageable page) {
		return dao.findByKey(key, page);
}
}
