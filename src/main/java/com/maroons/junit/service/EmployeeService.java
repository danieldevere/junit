package com.maroons.junit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maroons.junit.exception.EmployeeDuplicate;
import com.maroons.junit.exception.EmployeeNotFound;
import com.maroons.junit.model.Employee;
import com.maroons.junit.repository.EmployeeRepository;

@Component
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	public Employee getEmployee(Long employeeID) throws EmployeeNotFound {
		Employee employee = employeeRepository.getEmployeeById(employeeID);
		if(employee == null) {
			throw new EmployeeNotFound("employee not found in get employee");
		}
		return employeeRepository.getEmployeeById(employeeID);
	}
	
	public void saveEmployee(Employee employee) throws EmployeeDuplicate {
		Employee foundEmployee = employeeRepository.getEmployeeById(employee.getId());
		if(foundEmployee != null) {
			throw new EmployeeDuplicate("employee already exists in save employee");
		}
		employeeRepository.save(employee);
	}
	
	public void updateEmployee(Employee employee) throws EmployeeNotFound {
		Employee foundEmployee = employeeRepository.getEmployeeById(employee.getId());
		if(foundEmployee == null) {
			throw new EmployeeNotFound("can't find employee for update");
		}
		employeeRepository.save(employee);
	}
	
	public void deleteEmployee(Long employeeID) throws EmployeeNotFound {
		Employee foundEmployee = employeeRepository.getEmployeeById(employeeID);
		if(foundEmployee == null) {
			throw new EmployeeNotFound("employee not found in delete");
		}
		employeeRepository.delete(employeeID);
	}
}
