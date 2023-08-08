package com.asm.dao;

import java.lang.StackWalker.Option;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.asm.entity.Category;
import com.asm.entity.Specification;
import com.asm.entity.Users;

public interface UserDAO extends JpaRepository<Users, Integer>{
	@Query("SELECT u FROM Users u WHERE u.email = ?1")
    public Users findByEmail(String email);
    @Query("SELECT c FROM Users c WHERE c.status = true")
    Page<Users> findAllWhereStatusTrue(  Pageable page);
    @Query("SELECT s FROM Users s WHERE s.email LIKE %:email% and s.status=true")
    Page<Users> findTableByEmail( String email, Pageable page);

}
