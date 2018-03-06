package com.maroons.junit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.maroons.junit.exception.EmployeeDuplicate;
import com.maroons.junit.exception.EmployeeNotFound;
import com.maroons.junit.model.Employee;
import com.maroons.junit.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTests {
	
	public EmployeeServiceTests() {
		MockitoAnnotations.initMocks(this.getClass());
	}

	@Mock
	private EmployeeRepository employeeRepository;
	
	@InjectMocks
	private EmployeeService employeeService;
	
	@Before
	public void beforeTests() {
		List<Employee> employees = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			employees.add(new Employee(i, "Firstname", "Lastname"));
		}
		Answer<Employee> employeeRepositoryGetEmployeeByIDAnswer = new Answer<Employee>() {
			public Employee answer(InvocationOnMock invocation) throws Throwable {
				Long employeeID = invocation.getArgumentAt(0, Long.class);
				for(Employee employee : employees) {
					if(employee.getId() == employeeID) {
						return employee;
					}
				}
				return null;
			}
		};
		when(employeeRepository.getEmployeeById(any(Long.class))).thenAnswer(employeeRepositoryGetEmployeeByIDAnswer);
		
		Answer<Integer> employeeRepositorySave = new Answer<Integer>() {
			public Integer answer(InvocationOnMock invocation) throws Throwable {
				return 0;
			}
		};
		when(employeeRepository.save(any(Employee.class))).thenAnswer(employeeRepositorySave);
		
		Answer<Integer> employeeRepositoryDelete = new Answer<Integer>() {
			public Integer answer(InvocationOnMock invocation) throws Throwable {
				return 0;
			}
		};
		doAnswer(employeeRepositoryDelete).when(employeeRepository).delete(any(Long.class));
	}
	
	@Test
	public void saveEmployee() throws Exception {
		employeeService.saveEmployee(new Employee(10, "Dan", "DeVere"));
	}
	
	@Test(expected=EmployeeDuplicate.class)
	public void saveEmployeeDuplicate() throws Exception {
		employeeService.saveEmployee(new Employee(1, "Dan", "DeVere"));
	}
	
	@Test
	public void getEmployee() throws Exception {
		Employee employee = employeeService.getEmployee(new Long(1));
		assertThat(employee.getId()).isEqualTo(1);
	}
	
	@Test(expected=EmployeeNotFound.class)
	public void getEmployeeNotFound() throws Exception {
		Employee employee = employeeService.getEmployee(new Long(10));
		employee.getClass();
	}
	
	@Test
	public void updateEmployee() throws Exception {
		Employee employee = new Employee(1, "Dan", "DeVere");
		employeeService.updateEmployee(employee);
	}
	
	@Test(expected=EmployeeNotFound.class)
	public void updateEmployeeNotFound() throws Exception {
		Employee employee = new Employee(10, "Dan", "DeVere");
		employeeService.updateEmployee(employee);
	}
	
	@Test
	public void deleteEmployee() throws Exception {
		employeeService.deleteEmployee(new Long(1));
	}
	
	@Test(expected=EmployeeNotFound.class)
	public void deleteEmployeeNotFound() throws Exception {
		employeeService.deleteEmployee(new Long(10));
	}
}
