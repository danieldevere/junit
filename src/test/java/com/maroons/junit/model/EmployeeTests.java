package com.maroons.junit.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.maroons.junit.exception.AnnualReviewDuplicate;
import com.maroons.junit.exception.AnnualReviewNotFound;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeTests {
	
	private Employee employee;
	
	private List<AnnualReview> annualReviews;
	
	@Before
	public void beforeTests() {
		employee = new Employee(1, "Dan", "DeVere");
		annualReviews = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		for(int i = 2015; i <= calendar.get(Calendar.YEAR); i++) {
			annualReviews.add(new AnnualReview(i));
		}
	}
	
	@Test
	public void addAnnualReview() throws Exception {
		AnnualReview review = new AnnualReview(2018);
		employee.addAnnualReview(review);
		assertThat(employee.getReviews().get(0).getYear()).isEqualTo(2018);
	}
	
	@Test
	public void getAnnualReview() throws Exception {
		employee.setReviews(annualReviews);
		AnnualReview foundReview = employee.getAnnualReview(2018);
		assertThat(foundReview.getYear()).isEqualTo(2018);
	}
	
	@Test
	public void getCurrentReview() throws Exception {
		employee.setReviews(annualReviews);
		AnnualReview foundReview = employee.getCurrentReview();
		assertThat(foundReview.getYear()).isEqualTo(Calendar.getInstance().get(Calendar.YEAR));
	}
	
	@Test(expected=AnnualReviewDuplicate.class)
	public void addAnnualReviewDuplicate() throws Exception {
		employee.setReviews(annualReviews);
		employee.addAnnualReview(new AnnualReview(2015));
	}
	
	@Test(expected=AnnualReviewNotFound.class)
	public void getAnnualReviewNotExists() throws Exception {
		employee.setReviews(annualReviews);
		AnnualReview foundReview = employee.getAnnualReview(2014);
		foundReview.getClass();
	}
	
	@Test(expected=AnnualReviewNotFound.class)
	public void getCurrentReviewNotExists() throws Exception {
		AnnualReview foundReview = employee.getCurrentReview();
		foundReview.getClass();
	}
}
