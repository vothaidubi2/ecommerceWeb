package com.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asm.entity.Users;

public interface UserDAO extends JpaRepository<Users, String>{

}
