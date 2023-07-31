package com.asm.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.asm.entity.Product;

public interface ProductDAO extends JpaRepository<Product, Integer> {
	Page<Product> findAllByNameLikeAndPriceBetweenAndCategoryIdAndProducerIdAndStatus(String keywords, double minPrice,
			double maxPrice, Integer category, Integer producer, Boolean status, Pageable pageable);

	Page<Product> findAllByNameLikeAndPriceBetweenAndProducerIdAndStatus(String keywords, double minPrice,
			double maxPrice, Integer producer, Boolean status, Pageable pageable);

	Page<Product> findAllByNameLikeAndPriceBetweenAndCategoryIdAndStatus(String keywords, double minPrice,
			double maxPrice, Integer category, Boolean status, Pageable pageable);

	Page<Product> findAllByNameLikeAndPriceBetweenAndStatus(String keywords, double minPrice, double maxPrice,
			Boolean status, Pageable pageable);

	<T> T findByName(String name, Class<T> type);
}
