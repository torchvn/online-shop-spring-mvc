package com.example.demo.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Entity.Category;

public interface CategoryDao extends JpaRepository<Category, Integer> {
	@Query("SELECT c FROM Category c WHERE c.name LIKE ?1")
	Page<Category> fillToTable(String name, Pageable pageable);
}
