package com.asm.dao;

import java.lang.StackWalker.Option;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.asm.entity.Users;

public interface UserDAO extends JpaRepository<Users, Integer>{
	@Query("SELECT u FROM Users u WHERE u.email = ?1")
    public Users findByEmail(String email);

}
