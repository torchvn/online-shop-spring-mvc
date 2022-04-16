package com.example.demo.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Entity.Size;

public interface SizeDao extends JpaRepository<Size, Integer> {
	@Query("SELECT s FROM Size s WHERE s.name LIKE ?1")
	Page<Size> fillToTable(String name, Pageable pageable);
}
