package com.asm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.asm.dao.ImageDAO;
import com.asm.dao.ProductDAO;
import com.asm.dao.SpecificationDAO;
import com.asm.dao.SpecificationDetailsDAO;
import com.asm.dto.ImageDTO;
import com.asm.dto.ProductDTO;
import com.asm.dto.SpecificationDTO;
import com.asm.entity.Image;
import com.asm.entity.Product;
import com.asm.entity.Specification;
import com.asm.entity.SpecificationDetails;

@Service
public class ProductService {
	@Autowired
	private ProductDAO productRepository;
	@Autowired
	private ImageDAO imageRepository;
	@Autowired
	private SpecificationDAO specificationRepository;
	@Autowired
	private SpecificationDetailsDAO specificationDetailsRepository;

	public ResponseEntity<ProductDTO> createProduct(ProductDTO productDTO) {
		Product product = convertProductDTOToEntity(productDTO);
		// Save the product in the repository
		Product savedProduct = productRepository.save(product);
		if (productDTO.getImage() != null && !productDTO.getImage().isEmpty()) {
			List<Image> images = new ArrayList<>();
			for (ImageDTO imageDTO : productDTO.getImage()) {
				Image image = new Image();
				image.setUrl(imageDTO.getUrl());
				image.setProduct(savedProduct);
				images.add(image);
			}
			List<Image> saveImage = imageRepository.saveAll(images);
			savedProduct.setImages(saveImage);
		}

		// Create and associate the specifications with the product if provided
		if (productDTO.getSpecifications() != null && !productDTO.getSpecifications().isEmpty()) {
			List<Specification> specifications = new ArrayList<>();
			List<SpecificationDetails> specificationDetailsList = new ArrayList<>();
			for (SpecificationDTO specificationDTO : productDTO.getSpecifications()) {
				Specification specification = new Specification();
				specification.setId(specificationDTO.getId());
				specification.setKeys(specificationDTO.getKey());
				specification.setValue(specificationDTO.getValue());

				SpecificationDetails specificationDetails = new SpecificationDetails();
				specificationDetails.setProduct(savedProduct);
				specificationDetails.setSpecification(specification);
				specificationDetailsList.add(specificationDetails);
			}
			specificationRepository.saveAll(specifications);
			List<SpecificationDetails> saveSpecificationDetails = specificationDetailsRepository
					.saveAll(specificationDetailsList);
			savedProduct.setSpecificationDetailses(saveSpecificationDetails);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(mapToProductDTO(savedProduct));
	}

	public ResponseEntity<Map<String, Object>> getAllProducts() {
		List<ProductDTO> products = productRepository.findByStatus(true).stream().map(this::mapToProductDTO).toList();
		Map<String, Object> response = new HashMap<>();
		response.put("total", productRepository.findByStatus(true).size());
		response.put("data", products);
		return ResponseEntity.ok(response);
	}

	public ResponseEntity<ProductDTO> getOneProduct(Integer id) {
		if (!productRepository.existsById(id) || productRepository.findById(id).get().getStatus() == false) {
			return ResponseEntity.notFound().build();
		}
		ProductDTO products = mapToProductDTO(productRepository.findByIdAndStatus(id, true));
		return ResponseEntity.ok(products);
	}

	public ResponseEntity<ProductDTO> updateProduct(Integer id, ProductDTO updatedProductDTO) {
		// Check if the product exists and is active
		Product existingProduct = productRepository.findByIdAndStatus(id, true);
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
		existingSpecificationDetailsList.forEach(del -> specificationDetailsRepository.deleteById(del.getId()));
		List<Specification> updatedSpecificationsList = convertToSpecificationList(
				updatedProductDTO.getSpecifications());

		// Now update existing and add new SpecificationDetails
		List<SpecificationDetails> updatedSpecificationsDetailsList = new ArrayList<>();
		for (Specification updatedSpecification : updatedSpecificationsList) {
			SpecificationDetails specificationDetails = new SpecificationDetails();
			specificationDetails.setProduct(existingProduct);
			specificationDetails.setSpecification(updatedSpecification);
			updatedSpecificationsDetailsList.add(specificationDetails);
			specificationDetailsRepository.save(specificationDetails);
		}
		existingProduct.setSpecificationDetailses(updatedSpecificationsDetailsList);
		// Update images
		List<Image> existingImage = existingProduct.getImages();
		existingImage.forEach(del -> imageRepository.deleteById(del.getId()));
		List<Image> images = new ArrayList<>();
		for (ImageDTO imageDTO : updatedProductDTO.getImage()) {
			Image image = new Image();
			image.setUrl(imageDTO.getUrl());
			image.setProduct(existingProduct);
			images.add(image);
			imageRepository.save(image);
		}
		existingProduct.setImages(images);

		// Save the updated product in the database
//		Product updatedProduct = productRepository.save(existingProduct);

//		ProductDTO responseProductDTO = mapToProductDTO(updatedProduct);
		return ResponseEntity.ok(mapToProductDTO(existingProduct));
	}

	public ResponseEntity<ProductDTO> deleteProduct(Integer id) {
		// Check if the product exists and is active
		Product existingProduct = productRepository.findByIdAndStatus(id, true);
		if (existingProduct == null) {
			return ResponseEntity.notFound().build();
		}
//		existingProduct.getSpecificationDetailses().forEach(ietm -> {
//			specificationDetailsRepository.deleteById(ietm.getId());
//		});
//		existingProduct.getImages().forEach(item -> {
//			imageRepository.deleteById(item.getId());
//		});
//		productRepository.deleteById(id);
		existingProduct.setStatus(false);
		productRepository.save(existingProduct);
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

	private Product convertProductDTOToEntity(ProductDTO productDTO) {
		Product product = new Product();
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
		product.setQuantity(productDTO.getQuantity());
		product.setStatus(productDTO.getStatus());
		product.setCategory(productDTO.getCategory());
		product.setProducer(productDTO.getProducer());
		product.setStatus(true);
		product.setDescription(productDTO.getDescription());

		return product;
	}

	private List<Specification> convertToSpecificationList(List<SpecificationDTO> specificationDTOList) {
		List<Specification> specificationList = new ArrayList<>();
		for (SpecificationDTO specificationDTO : specificationDTOList) {
			// Check if specificationDTO has a valid Specification object
			Specification specification = new Specification();
			specification.setId(specificationDTO.getId());
			specification.setKeys(specificationDTO.getKey());
			specification.setValue(specificationDTO.getValue());
			specificationList.add(specification);
		}
		return specificationList;
	}

//	private ProductDTO convertEntityToProductDTO(Product product) {
//		ProductDTO productDTO = new ProductDTO();
//		productDTO.setId(product.getId());
//		productDTO.setName(product.getName());
//		productDTO.setPrice(product.getPrice());
//		productDTO.setQuantity(product.getQuantity());
//		productDTO.setStatus(product.getStatus());
//		productDTO.setCategory(product.getCategory());
//		productDTO.setProducer(product.getProducer());
//		productDTO.setDescription(product.getDescription());
//		List<SpecificationDTO> specifications = product.getSpecificationDetailses().stream()
//				.map(specificationDetails -> {
//					SpecificationDTO specificationDTO = new SpecificationDTO();
//					Specification specification = specificationDetails.getSpecification();
//					specificationDTO.setId(specification.getId());
//					specificationDTO.setKey(specification.getKeys());
//					specificationDTO.setValue(specification.getValue());
//					return specificationDTO;
//				}).collect(Collectors.toList());
//		productDTO.setSpecifications(specifications);
//		List<ImageDTO> imageDTOs = product.getImages().stream()
//				.map(image -> new ImageDTO(image.getId(), image.getUrl())).collect(Collectors.toList());
//		productDTO.setImage(imageDTOs);
//		// Map other properties as needed based on your data model
//
//		return productDTO;
//	}
}