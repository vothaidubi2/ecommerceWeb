package com.asm.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asm.entity.Specification;
import com.asm.entity.Users;
import com.asm.service.StatisticalService;

@CrossOrigin("*")
@RestController

public class SpecificationRestController {
	@Autowired
	StatisticalService SS;
	@GetMapping("api/specification/findall")
	public ResponseEntity<Page<Specification>> findAll(Pageable page) {
	  System.err.println(SS.findAll(page));
	  return ResponseEntity.ok(SS.findAll(page));
	}
	@GetMapping("api/specification/findByKey/{key}")
	public Page<Specification> findByKey(@PathVariable("key")String key,Pageable page) {
		return SS.findByKey(key,page);
	}
	@PutMapping("api/specification/delete")
	public void delete(@RequestBody Specification sp) {
		 SS.deleteById(sp);
	}
	@PostMapping("api/specification/create")
	public Specification create( @RequestBody Specification sp) {

		SS.save(sp);
		return sp;
	}
	@PutMapping("api/specification/update/{id}")
	public Specification update(@RequestBody Specification sp,@PathVariable ("id") Integer id) {
		SS.save(sp);
		return sp;
	}
}
