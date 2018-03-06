package com.maroons.junit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maroons.junit.model.PerformanceNote;

@Repository
public interface PerformanceNoteRepository extends JpaRepository<PerformanceNote, Long> {
	
}
