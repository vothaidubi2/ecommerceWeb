package com.asm.service;

import java.util.List;

import com.asm.entity.Category;
import com.asm.entity.Image;
import com.asm.entity.Producer;
import com.asm.entity.Specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Integer id;
    private String name;
    private Double price;
    private Integer quantity;
    private Boolean status;
    private Category category;
    private Producer producer;
    private Specification specifications;
    private List<ImageDTO> image;
    // Constructors, getters, and setters
}