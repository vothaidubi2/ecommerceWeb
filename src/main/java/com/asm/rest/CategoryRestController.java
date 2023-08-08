package com.asm.rest;

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
import org.springframework.web.bind.annotation.RestController;

import com.asm.entity.Category;
import com.asm.entity.Specification;
import com.asm.service.CategoryService;
@CrossOrigin("*")
@RestController
public class CategoryRestController {
	@Autowired
	CategoryService CS;
	
	@GetMapping("api/category/findall")
	public ResponseEntity<Page<Category>>  findAll(Pageable page) {
		CS.findAll(page);
	return   ResponseEntity.ok(CS.findAll(page));
	}
	@GetMapping("api/category/findallStatusTrue")
	public ResponseEntity<Page<Category>>  findAllStatusTrue(Pageable page) {
		CS.findAllStatusTrue(page);
	return   ResponseEntity.ok(CS.findAllStatusTrue	(page));
	}
//	@GetMapping("api/category/findByKey/{key}")
//	public Page<Specification> findByKey(@PathVariable("key")String key,Pageable page) {
//		return CS.findByKey(key,page);
//	}
	@PutMapping("api/category/delete")
	public ResponseEntity<Category> delete(@RequestBody Category sp) {
		 CS.deleteById(sp);
		 return ResponseEntity.ok(CS.deleteById(sp));
	}
	@PostMapping("api/category/create")
	public ResponseEntity<Category> create( @RequestBody Category sp) {
		CS.save(sp);
		return ResponseEntity.ok(CS.save(sp));
	}
	@PutMapping("api/category/update/{id}")
	public ResponseEntity<Category> update(@RequestBody Category sp,@PathVariable ("id") Integer id) {
		CS.save(sp);
		return ResponseEntity.ok(CS.save(sp));
	}
}
