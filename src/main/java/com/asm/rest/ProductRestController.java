package com.asm.rest;

import java.util.List;
import java.util.Optional;

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
	public ResponseEntity<List<Product>> getAllProducts(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "7") int size, @RequestParam(required = false) Optional<String> keywords,
			@RequestParam(required = false) Integer categoryId, @RequestParam(required = false) Integer producerId,
			@RequestParam(required = false) Optional<Double> minrange,
			@RequestParam(required = false) Optional<Double> maxrange,
			@RequestParam(defaultValue = "price,asc") String[] sort) {
		System.out.println("categoryId: " + categoryId);
		System.out.println("producerId: " + producerId);

		Pageable pageable = PageRequest.of(page, size, getSortDirection(sort));

		String name = keywords.orElse("");
		double minPrice = minrange.orElse(0.0);
		double maxPrice = maxrange.orElse(Double.MAX_VALUE);

		Page<Product> productPage = getByCategoryAndProdcer(name, minPrice, maxPrice, categoryId, producerId, pageable);
		List<Product> products = productPage.getContent();

		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	private Page<Product> getByCategoryAndProdcer(String keywords, double minPrice, double maxPrice, Integer categoryId,
			Integer producerId, Pageable pageable) {
		if (categoryId != null && producerId != null) {
			return productDAO.findAllByNameLikeAndPriceBetweenAndCategoryIdAndProducerIdAndStatus("%" + keywords + "%",
					minPrice, maxPrice, categoryId, producerId, true, pageable);
		} else if (categoryId != null) {
			return productDAO.findAllByNameLikeAndPriceBetweenAndCategoryIdAndStatus("%" + keywords + "%", minPrice,
					maxPrice, categoryId, true, pageable);
		} else if (producerId != null) {
			return productDAO.findAllByNameLikeAndPriceBetweenAndProducerIdAndStatus("%" + keywords + "%", minPrice,
					maxPrice, producerId, true, pageable);
		}
		return productDAO.findAllByNameLikeAndPriceBetweenAndStatus("%" + keywords + "%", minPrice, maxPrice, true,
				pageable);
	}

	private Sort getSortDirection(String[] sort) {
		if (sort[1].equalsIgnoreCase("desc")) {
			return Sort.by(Sort.Direction.DESC, sort[0]);
		}
		return Sort.by(Sort.Direction.ASC, sort[0]);
	}
}
