package com.example.demo.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Entity.UserRole;

public interface UserRoleDao extends JpaRepository<UserRole, Integer> {
	@Query("SELECT u.id FROM UserRole u WHERE u.user.id = ?1")
	public int findIdUserRole(int id);
	@Query(value="SELECT * FROM User_Role u WHERE u.User_ID = ?1",nativeQuery = true)
	public UserRole findUserRoleByUserId(int id);
}
