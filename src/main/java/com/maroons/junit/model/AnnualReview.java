package com.maroons.junit.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="annualReview")
public class AnnualReview {
	@Id
	@GeneratedValue
	private long id;
	
	private Long employeeId;
	
	private int year;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="reviewId")
	private List<PerformanceNote> performanceNotes;
	
	public AnnualReview() {
		this.performanceNotes = new ArrayList<>();
	}
	
	public AnnualReview(int year) {
		this.year = year;
		this.performanceNotes = new ArrayList<>();
	}
	
	public void addPerformanceNote(PerformanceNote note) {
		this.performanceNotes.add(note);
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public List<PerformanceNote> getPerformanceNotes() {
		return performanceNotes;
	}

	public void setPerformanceNotes(List<PerformanceNote> performanceNotes) {
		this.performanceNotes = performanceNotes;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	
	
	
	
}
