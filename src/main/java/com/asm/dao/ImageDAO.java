package com.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asm.entity.Image;

public interface ImageDAO extends JpaRepository<Image, Integer>{

}
