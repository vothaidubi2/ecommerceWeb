package com.asm.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asm.dao.CategoryDAO;
import com.asm.dto.ProductDTO;
import com.asm.service.CategoryService;
import com.asm.service.ProductService;
import com.google.auth.oauth2.IdTokenProvider.Option;


@Controller
public class HomeController {
	@Autowired
	CategoryService cs;
	@Autowired
	ProductService ps;
	@RequestMapping("/home")
	public String home(Model model) {
	    ResponseEntity<Map<String, Object>> responseEntity = ps.getAllProducts();

        // Extract the response map from the ResponseEntity
        Map<String, Object> responseMap = responseEntity.getBody();

        // Extract the "total", "data", and "data[0]" values from the map
        List<ProductDTO> data = (List<ProductDTO>) responseMap.get("data");
        List<ProductDTO> firstThreeProducts = data != null ? data.subList(0, Math.min(data.size(), 5)) : null;

        // Add the extracted values as attributes to the model
//        model.addAttribute("product", firstProduct.getImage().get(0));
        model.addAttribute("listproduct", firstThreeProducts);
        
        // Print the first 5 values from the Map

		Pageable pageable =PageRequest.of(0, 4);
		model.addAttribute("category", cs.findAll(pageable ));
		return "home";
	}
}
