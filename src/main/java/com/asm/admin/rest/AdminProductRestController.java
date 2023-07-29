package com.asm.admin.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.asm.dao.ProductDAO;
import com.asm.entity.Product;
import com.asm.entity.Specification;
import com.asm.entity.SpecificationDetails;
import com.asm.service.ImageDTO;
import com.asm.service.ProductDTO;

@RestController
@RequestMapping("/admin/api")
public class AdminProductRestController {
	@Autowired
	ProductDAO dao;

	@GetMapping("/products")
	public ResponseEntity<Map<String, Object>> getAllProducts() {
		List<ProductDTO> products = dao.findByStatus(true).stream().map(this::mapToProductDTO).toList();
		Map<String, Object> response = new HashMap<>();
		response.put("total", dao.findByStatus(true).size());
		response.put("data", products);
		return ResponseEntity.ok(response);
	}

	private ProductDTO mapToProductDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setPrice(product.getPrice());
		productDTO.setQuantity(product.getQuantity());
		productDTO.setStatus(product.getStatus());
		productDTO.setCategory(product.getCategory());
		productDTO.setProducer(product.getProducer());
		List<Specification> specifications = product.getSpecificationDetailses().stream()
				.map(SpecificationDetails::getSpecification).collect(Collectors.toList());
		productDTO.setSpecifications(specifications);
		List<ImageDTO> imageDTOs = product.getImages().stream()
				.map(image -> new ImageDTO(image.getId(), image.getUrl())).collect(Collectors.toList());
		productDTO.setImage(imageDTOs);
		return productDTO;
	}
}
