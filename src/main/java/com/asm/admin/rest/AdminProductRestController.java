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

import com.asm.dao.ImageDAO;
import com.asm.dao.ProductDAO;
import com.asm.dao.SpecificationDAO;
import com.asm.dao.SpecificationDetailsDAO;
import com.asm.entity.Image;
import com.asm.entity.Product;
import com.asm.entity.Specification;
import com.asm.entity.SpecificationDetails;
import com.asm.service.ImageDTO;
import com.asm.service.ProductDTO;
import com.asm.service.ProductService;
import com.asm.service.SpecificationDTO;

@RestController
@RequestMapping("/admin/api")
public class AdminProductRestController {
	@Autowired
	ProductDAO dao;
	@Autowired
	SpecificationDAO Specdao;
	@Autowired
	SpecificationDetailsDAO Specdetailsdao;
	@Autowired
	ImageDAO imagedao;
	@Autowired
	ProductService productService;

	@GetMapping("/products")
	public ResponseEntity<Map<String, Object>> getAllProducts() {
		List<ProductDTO> products = dao.findByStatus(true).stream().map(this::mapToProductDTO).toList();
		Map<String, Object> response = new HashMap<>();
		response.put("total", dao.findByStatus(true).size());
		response.put("data", products);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<ProductDTO> getOneProduct(@PathVariable("id") Integer id) {
		if (!dao.existsById(id) || dao.findById(id).get().getStatus() == false) {
			return ResponseEntity.notFound().build();
		}
		ProductDTO products = mapToProductDTO(dao.findByIdAndStatus(id, true));
		return ResponseEntity.ok(products);
	}

	@PostMapping("/products")
	public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
		ProductDTO createdProduct = productService.createProduct(productDTO);
		return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
	}

	@PutMapping("/product/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") Integer id,
			@RequestBody ProductDTO updatedProductDTO) {
		// Check if the product exists and is active
		Product existingProduct = dao.findByIdAndStatus(id, true);
		if (existingProduct == null) {
			return ResponseEntity.notFound().build();
		}

		// Update basic product details
		existingProduct.setName(updatedProductDTO.getName());
		existingProduct.setPrice(updatedProductDTO.getPrice());
		existingProduct.setQuantity(updatedProductDTO.getQuantity());
		existingProduct.setStatus(updatedProductDTO.getStatus());
		existingProduct.setCategory(updatedProductDTO.getCategory());
		existingProduct.setProducer(updatedProductDTO.getProducer());
		existingProduct.setDescription(updatedProductDTO.getDescription());

		List<SpecificationDetails> existingSpecificationDetailsList = existingProduct.getSpecificationDetailses();
		existingSpecificationDetailsList.forEach(del -> Specdetailsdao.deleteById(del.getId()));
		List<Specification> updatedSpecificationsList = convertToSpecificationList(
				updatedProductDTO.getSpecifications());

		// Now update existing and add new SpecificationDetails
		List<SpecificationDetails> updatedSpecificationsDetailsList = new ArrayList<>();
		for (Specification updatedSpecification : updatedSpecificationsList) {
			SpecificationDetails specificationDetails = new SpecificationDetails();
			specificationDetails.setProduct(existingProduct);
			specificationDetails.setSpecification(updatedSpecification);
			updatedSpecificationsDetailsList.add(specificationDetails);
			Specdetailsdao.save(specificationDetails);
		}
		existingProduct.setSpecificationDetailses(updatedSpecificationsDetailsList);
		// Update images
		List<Image> existingImages = existingProduct.getImages();
		List<ImageDTO> updatedImageDTOs = updatedProductDTO.getImage();
		for (int i = 0; i < Math.min(existingImages.size(), updatedImageDTOs.size()); i++) {
			Image existingImage = existingImages.get(i);
			ImageDTO updatedImageDTO = updatedImageDTOs.get(i);
			existingImage.setUrl(updatedImageDTO.getUrl());
		}

		// Add new images
		List<ImageDTO> newImageDTOs = updatedImageDTOs.subList(existingImages.size(), updatedImageDTOs.size());
		for (ImageDTO newImageDTO : newImageDTOs) {
			Image newImage = new Image();
			newImage.setProduct(existingProduct);
			newImage.setUrl(newImageDTO.getUrl());
			existingProduct.getImages().add(newImage);
		}

		// Remove extra images
		int numExtraImages = existingImages.size() - updatedImageDTOs.size();
		for (int i = 0; i < numExtraImages; i++) {
			Image imageToRemove = existingImages.get(updatedImageDTOs.size() + i);
			existingProduct.getImages().remove(imageToRemove);
		}

		// Save the updated product in the database
		Product updatedProduct = dao.save(existingProduct);

		// Map the updated product to DTO and return the response
		ProductDTO responseProductDTO = mapToProductDTO(updatedProduct);
		return ResponseEntity.ok(responseProductDTO);
	}

	@DeleteMapping("/product/{id}")
	public ResponseEntity<ProductDTO> deleteProduct(@PathVariable("id") Integer id) {
		// Check if the product exists and is active
		Product existingProduct = dao.findByIdAndStatus(id, true);
		if (existingProduct == null) {
			return ResponseEntity.notFound().build();
		}
		existingProduct.getSpecificationDetailses().forEach(ietm ->{
			Specdetailsdao.deleteById(ietm.getId());			
		});
		existingProduct.getImages().forEach(item->{
			imagedao.deleteById(item.getId());			
		});
		dao.deleteById(id);
		return ResponseEntity.ok().build();
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
		productDTO.setDescription(product.getDescription());
		// Convert List<Specification> to List<SpecificationDTO>
		List<SpecificationDTO> specifications = product.getSpecificationDetailses().stream()
				.map(specificationDetails -> {
					SpecificationDTO specificationDTO = new SpecificationDTO();
					Specification specification = specificationDetails.getSpecification();
					specificationDTO.setId(specification.getId());
					specificationDTO.setKey(specification.getKeys());
					specificationDTO.setValue(specification.getValue());
					return specificationDTO;
				}).collect(Collectors.toList());
		productDTO.setSpecifications(specifications);
		List<ImageDTO> imageDTOs = product.getImages().stream()
				.map(image -> new ImageDTO(image.getId(), image.getUrl())).collect(Collectors.toList());
		productDTO.setImage(imageDTOs);
		return productDTO;
	}

	private List<Specification> convertToSpecificationList(List<SpecificationDTO> specificationDTOList) {
		List<Specification> specificationList = new ArrayList<>();
		for (SpecificationDTO specificationDTO : specificationDTOList) {
			// Check if specificationDTO has a valid Specification object
			Specification specification = new Specification();
			specification.setId(specificationDTO.getId());
			specification.setKeys(specificationDTO.getKey());
			specification.setValue(specificationDTO.getValue());
			// Any other fields you may need to set

			specificationList.add(specification);
		}
		return specificationList;
	}
}