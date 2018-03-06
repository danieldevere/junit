package com.maroons.junit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maroons.junit.model.AnnualReview;
@Repository
public interface AnnualReviewRepository extends JpaRepository<AnnualReview, Long>{
	
}
