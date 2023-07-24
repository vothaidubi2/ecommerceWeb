package com.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asm.entity.Producer;

public interface ProducerDAO extends JpaRepository<Producer, Integer>{

}
