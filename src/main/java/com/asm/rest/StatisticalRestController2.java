package com.asm.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asm.dao.SpecificationDAO;
import com.asm.dao.UserDAO;
import com.asm.entity.Specification;
import com.asm.entity.Users;

@CrossOrigin("*")
@RestController

public class StatisticalRestController2 {
	@Autowired
	SpecificationDAO dao;
	@GetMapping("api/specification/findall")
	public Page<Specification>  findAll(Pageable page) {
	return  dao.findAll(page);
	}
	@GetMapping("api/specification/findByKey/{key}")
	public Page<Specification> findByKey(@PathVariable("key")String key,Pageable page) {
		return dao.findByKey(key,page);
	}
	@DeleteMapping("api/specification/delete/{id}")
	public void delete(@PathVariable("id")Integer id) {
		System.err.println(id);
		 dao.deleteById(id);
	}
	@PostMapping("api/specification/create")
	public Specification create( @RequestBody Specification sp) {

		dao.save(sp);
		return sp;
	}
	@PutMapping("api/specification/update/{id}")
	public Specification update(@RequestBody Specification sp,@PathVariable ("id") Integer id) {
		dao.save(sp);
		return sp;
	}
}
