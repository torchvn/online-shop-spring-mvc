package com.example.demo.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Entity.Role;

public interface RoleDao extends JpaRepository<Role, Integer> {
	@Query("SELECT u.role.name FROM UserRole u WHERE u.user.id = :uid")
	List<String> getRoleNames(@Param("uid") Integer id);
}
