package com.asm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asm.dao.ImageDAO;
import com.asm.dao.ProductDAO;
import com.asm.dao.SpecificationDAO;
import com.asm.dao.SpecificationDetailsDAO;
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
	
	public ProductDTO createProduct(ProductDTO productDTO) {
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
            List<SpecificationDetails> saveSpecificationDetails = specificationDetailsRepository.saveAll(specificationDetailsList);
            savedProduct.setSpecificationDetailses(saveSpecificationDetails);
        }

        return convertEntityToProductDTO(savedProduct);
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

    private ProductDTO convertEntityToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setStatus(product.getStatus());
        productDTO.setCategory(product.getCategory());
        productDTO.setProducer(product.getProducer());
        productDTO.setDescription(product.getDescription());
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
        // Map other properties as needed based on your data model

        return productDTO;
    }
}