package com.example.demo.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Model.FillProSize;

public interface FillProSizeDao extends JpaRepository<FillProSize, Integer> {
	@Query("SELECT new FillProSize(p.id, p.product.name, p.size.name) FROM ProductSize p WHERE p.size.name LIKE ?1")
	Page<FillProSize> fillToTable(String name, Pageable pageable);
}
