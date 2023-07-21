package com.asm.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asm.dao.ProductDAO;
import com.asm.entity.Product;

@RestController
public class ProductRestController {
	@Autowired
	ProductDAO productDAO;
	
	@GetMapping("/api/products")
	public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "price,asc") String[] sort) {

		Pageable pageable = PageRequest.of(page, size, getSortDirection(sort));

        Page<Product> productPage = productDAO.findAll(pageable);
        List<Product> products = productPage.getContent();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
	 private Sort getSortDirection(String[] sort) {
	        if (sort[1].equalsIgnoreCase("desc")) {
	            return Sort.by(Sort.Direction.DESC, sort[0]);
	        }
	        return Sort.by(Sort.Direction.ASC, sort[0]);
	    }
}
