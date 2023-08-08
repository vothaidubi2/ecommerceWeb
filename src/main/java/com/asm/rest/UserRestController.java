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

import com.asm.dao.SpecificationDAO;
import com.asm.dao.UserDAO;
import com.asm.entity.Users;
import com.asm.entity.Specification;
import com.asm.entity.Users;
import com.asm.service.AuthService;

@CrossOrigin("*")
@RestController

public class UserRestController {
	@Autowired
	AuthService as;
	
	@GetMapping("api/user/findByEmail/{key}")
	public Users findByEmail(@PathVariable("key") String email) {
		

		return as.findByEmail(email);
	}
	@GetMapping("api/user/findTableByEmail/{key}")
	public Page<Users> findByKey(@PathVariable("key")String key,Pageable page) {
		return as.findTableByEmail(key,page);
	}
	
	@GetMapping("api/user/findall")
	public ResponseEntity<Page<Users>>  findAll(Pageable page) {
	return   ResponseEntity.ok(as.findAll(page));
	}


	@PutMapping("api/user/delete")
	public ResponseEntity<Users> delete(@RequestBody Users sp) {
		 return ResponseEntity.ok(as.deleteUser(sp));
	}
	@PostMapping("api/user/create")
	public ResponseEntity<Users> create( @RequestBody Users sp) {
		return ResponseEntity.ok(as.save(sp));
	}
	@PutMapping("api/user/update/{id}")
	public ResponseEntity<Users> update(@RequestBody Users sp,@PathVariable ("id") Integer id) {
		return ResponseEntity.ok(as.save(sp));
	}
}
