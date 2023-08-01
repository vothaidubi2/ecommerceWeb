package com.asm.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asm.dao.ProductDAO;
import com.asm.entity.projection.ProductDetailsProjection;
import com.asm.response.ProductDetailsResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductdetailController {

	private final ProductDAO productDAO;

	// .../product/details?name=...
	@GetMapping("/product/details")
	public String view(@RequestParam("name") String name, Model model) {
		ProductDetailsProjection projection = productDAO.findByName(name, ProductDetailsProjection.class);
		
		ProductDetailsResponse resp = toResponse(projection);
		
		model.addAttribute("detail", resp);
      model.addAttribute("productId", projection.getId());
		return "productdetail";
	}
	
	private ProductDetailsResponse toResponse(ProductDetailsProjection projection) {
		List<String> productImages = projection.getImages().stream().map(image -> image.getUrl()).toList();
		
		return ProductDetailsResponse.builder()
				.name(projection.getName())
				.price(projection.getPrice())
				.categoryName(projection.getCategory().getName())
				.images(productImages)
				.build();
	}
}
