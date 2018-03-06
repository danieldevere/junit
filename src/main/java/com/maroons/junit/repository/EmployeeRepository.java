package com.maroons.junit.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.maroons.junit.model.Employee;
@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
	List<Employee> findAll();
	
	Employee getEmployeeById(Long id);
}
