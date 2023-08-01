package com.asm.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

public class UserRestController {
	@Autowired
	UserDAO dao;
	@GetMapping("api/user/findByEmail/{key}")
	public Users findByEmail(@PathVariable("key") String email) {
		

		return dao.findByEmail(email);
	}
}
