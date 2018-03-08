package com.maroons.junit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="performanceNote")
public class PerformanceNote {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private Long reviewId;
	
	private String noteText;
	
	public PerformanceNote() {}
	
	public PerformanceNote(String text) {
		this.noteText = text;
	}

	public String getNoteText() {
		return noteText;
	}

	public void setNoteText(String noteText) {
		this.noteText = noteText;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getReviewId() {
		return reviewId;
	}

	public void setReviewId(long reviewId) {
		this.reviewId = reviewId;
	}
	
	
	
}
