package com.asm.admin.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asm.dao.CategoryDAO;
import com.asm.dao.ProducerDAO;
import com.asm.dao.SpecificationDAO;
import com.asm.entity.Category;
import com.asm.entity.Producer;
import com.asm.entity.Specification;

@RestController
@RequestMapping("/admin/api")
public class AdminServiceRestController {
	@Autowired
	SpecificationDAO specificationDao;
	@Autowired
	ProducerDAO producrDao;
	@Autowired
	CategoryDAO cateDao;

	@GetMapping("/specification")
	public ResponseEntity<List<Specification>> getAllSpec() {
		List<Specification> list = specificationDao.findAll();
		return ResponseEntity.ok(list);
	}

	@GetMapping("/producer")
	public ResponseEntity<List<Producer>> getAllProducer() {
		List<Producer> list = producrDao.findAll();
		return ResponseEntity.ok(list);
	}

	@GetMapping("/category")
	public ResponseEntity<List<Category>> getAllCategory() {
		List<Category> list = cateDao.findAll();
		return ResponseEntity.ok(list);
	}
}
