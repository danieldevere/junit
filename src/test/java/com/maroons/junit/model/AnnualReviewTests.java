package com.maroons.junit.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnnualReviewTests {
	
	private AnnualReview annualReview;
	
	@Before
	public void beforeTests() {
		annualReview = new AnnualReview(2018);
	}
	
	@Test
	public void addPerformanceNote() {
		PerformanceNote note = new PerformanceNote("This is the note text");
		annualReview.addPerformanceNote(note);
		assertThat(annualReview.getPerformanceNotes().size()).isEqualTo(1);
	}
}
