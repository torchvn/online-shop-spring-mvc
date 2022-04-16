package com.example.demo.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Model.FillProCate;

public interface FillProCateDao extends JpaRepository<FillProCate, Integer> {
	@Query("SELECT new FillProCate(p.id, p.product.name, p.category.name) FROM ProductCate p WHERE p.category.name LIKE ?1")
	Page<FillProCate> fillToTable(String name, Pageable pageable);
}

