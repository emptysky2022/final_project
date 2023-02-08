package com.campers.camfp.repository;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.campers.camfp.entity.Camp;
import com.campers.camfp.repository.CampRepository;

@SpringBootTest
public class CampRepositoryTests {

	private final CampRepository campRepository;
	private final CampReviewRepository campReviewRepository;
	private final CampHistoryRepository campHistoryRepository;
	private final CampCalanderRepository campCalanderRepository;

	@Autowired
	public CampRepositoryTests(CampRepository a, CampReviewRepository b, CampHistoryRepository c,
			CampCalanderRepository d) {
		this.campRepository = a;
		this.campReviewRepository = b;
		this.campHistoryRepository = c;
		this.campCalanderRepository = d;
	}

	public void test1() {
		selectAll(type.camp);
	}

	public void selectAll(type camp) {

		switch (camp) {
		
		case camp: 
				
			break;
			
		case campReview: 
			
			break;
			
		case campHistory: 
			
			break;
			
		case campCalander: 
			
			break;
		
		default:
			break;
			
		}
	}

	@Test
	public void Camptest(JpaRepository<?, ?> data) {

		Optional<?> test = Optional.ofNullable(data);

	}

	public enum type {
		camp, campReview, campHistory, campCalander
	}

}
