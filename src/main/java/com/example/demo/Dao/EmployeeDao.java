package com.example.demo.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Entity.Employee;
import com.example.demo.Model.EmployeeReport;

public interface EmployeeDao extends JpaRepository<Employee, Integer> {
	@Query("SELECT new EmployeeReport(e.user, e.position, e.id, e.startDay) FROM Employee e WHERE e.user.fullname LIKE ?1")
	Page<EmployeeReport> fillToTable(String fullname, Pageable pageable);
}