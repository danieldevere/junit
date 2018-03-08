package com.maroons.junit.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maroons.junit.exception.AnnualReviewDuplicate;
import com.maroons.junit.exception.AnnualReviewNotFound;

@Entity
@Table(name="employee")
public class Employee {
	
	@Column(unique=true)
	@Id
	private Long id;
	
	private String firstname;
	
	@Column(nullable=false)
	private String lastname;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="employeeId")
	private List<AnnualReview> reviews;
	
	public Employee() {
		this.reviews = new ArrayList<>();
	}
	
	public Employee(long id, String firstname, String lastname) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.reviews = new ArrayList<>();
	}
	
	public void addAnnualReview(AnnualReview annualReview) throws AnnualReviewDuplicate {
		for(AnnualReview review : this.reviews) {
			if(review.getYear() == annualReview.getYear()) {
				throw new AnnualReviewDuplicate("Can only have one annual review for each year");
			}
		}
		this.reviews.add(annualReview);
	}
	
	@JsonIgnore
	public AnnualReview getAnnualReview(int year) throws AnnualReviewNotFound {
		for(AnnualReview review : this.reviews) {
			if(review.getYear() == year) {
				return review;
			}
		}
		throw new AnnualReviewNotFound("Review not found year:" + year);
	}
	
	@JsonIgnore
	public AnnualReview getCurrentReview() throws AnnualReviewNotFound {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		for(AnnualReview review : this.reviews) {
			if(review.getYear() == currentYear) {
				return review;
			}
		}
		throw new AnnualReviewNotFound("review not found for current year");
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public List<AnnualReview> getReviews() {
		return reviews;
	}

	public void setReviews(List<AnnualReview> reviews) {
		this.reviews = reviews;
	}
	
	
}
