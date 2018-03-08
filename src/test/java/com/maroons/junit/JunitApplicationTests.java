package com.maroons.junit;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.maroons.junit.model.AnnualReview;
import com.maroons.junit.model.Employee;
import com.maroons.junit.model.PerformanceNote;
import com.maroons.junit.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class JunitApplicationTests {
	
	@Autowired
	private TestRestTemplate template;
	
	@LocalServerPort
	private int port;
	
	private Employee employee;
	
	private HttpHeaders headers;
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	public void contextLoads() {
	}
	
	
	@Before
	public void beforeTests() {
		employee = new Employee(1, "Daniel", "DeVere");
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	}
	
	@After
	public void afterTests() {
		employeeRepository.deleteAll();
	}
	
	@Test
	public void addEmployeeThenSearch() {
		ResponseEntity<String> response1 = template
				.postForEntity("/employee", employee, String.class);
		
		assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		assertThat(response1.getBody()).isEqualTo("Added employee");
		
		ResponseEntity<Employee> response2 = template
				.getForEntity("/employee/" + employee.getId(), Employee.class);
		
		assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		assertThat(response2.getBody().getFirstname()).isEqualTo(employee.getFirstname());
		
		assertThat(response2.getBody().getLastname()).isEqualTo(employee.getLastname());
	}

	@Test
	public void addEmployeeThenUpdateThenSearch() throws Exception {
		ResponseEntity<String> response1 = template
				.postForEntity("/employee", employee, String.class);
		
		assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		assertThat(response1.getBody()).isEqualTo("Added employee");
		
		AnnualReview review = new AnnualReview(2018);
		
		review.addPerformanceNote(new PerformanceNote("hello"));
		
		employee.addAnnualReview(review);
		
		employee.setFirstname("Dan");
		
		employee.setLastname("Devere");
		Gson gson = new Gson();
		
		String employeeJson = gson.toJson(employee);
		
		HttpEntity<String> request = new HttpEntity<String>(employeeJson, headers);
		
		ResponseEntity<String> response2 = template
				.exchange("/employee", HttpMethod.PUT, request, String.class);
		
		assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		assertThat(response2.getBody()).isEqualTo("updated employee");
		
		ResponseEntity<Employee> response3 = template
				.getForEntity("/employee/" + employee.getId(), Employee.class);
		
		assertThat(response3.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		assertThat(response3.getBody().getFirstname()).isEqualTo(employee.getFirstname());
		
		assertThat(response3.getBody().getLastname()).isEqualTo(employee.getLastname());
		
		assertThat(response3.getBody().getAnnualReview(2018)
				.getPerformanceNotes().get(0).getNoteText()).isEqualTo("hello");
	}

	@Test
	public void addEmployeeThenDelete() {
		ResponseEntity<String> response1 = template
				.postForEntity("/employee", employee, String.class);
		assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response1.getBody()).isEqualTo("Added employee");
		ResponseEntity<String> response2 = template
				.exchange("/employee/" + employee.getId(), HttpMethod.DELETE, null, String.class);
		assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response2.getBody()).isEqualTo("employee deleted");
	}

}
