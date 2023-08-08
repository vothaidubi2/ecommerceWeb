package com.asm.admin.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.asm.dto.ProductDTO;
import com.asm.service.ProductService;

@RestController
@RequestMapping("/admin/api")
public class AdminProductRestController {
	
	@Autowired
	ProductService productService;

	@GetMapping("/products")
	public ResponseEntity<Map<String, Object>> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<ProductDTO> getOneProduct(@PathVariable("id") Integer id) {
		return productService.getOneProduct(id);
	}

	@PostMapping("/products")
	public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
		return productService.createProduct(productDTO);
	}

	@PutMapping("/product/update/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") Integer id,
			@RequestBody ProductDTO updatedProductDTO) {
		return productService.updateProduct(id, updatedProductDTO);
	}

	@PutMapping("/product/delete/{id}")
	public ResponseEntity<ProductDTO> deleteProduct(@PathVariable("id") Integer id) {
		return productService.deleteProduct(id);
	}

	

	
}