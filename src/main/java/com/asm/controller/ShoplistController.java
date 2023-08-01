package com.asm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import com.asm.dao.CategoryDAO;
import com.asm.dao.ProducerDAO;
import com.asm.entity.Category;
import com.asm.entity.Producer;

@Controller
public class ShoplistController {
	@Autowired
	CategoryDAO category;
	@Autowired
	ProducerDAO producer;

	@GetMapping("/shoplist")
	public String shoplist(@RequestParam(required = false) String keywords) {
		return "shoplist";
	}

	@ModelAttribute("category")
	public List<Category> listCate() {
		List<Category> list = category.findAll();
		return list;
	}

	@ModelAttribute("producer")
	public List<Producer> listPro() {
		List<Producer> list = producer.findAll();
		return list;
	}
}
