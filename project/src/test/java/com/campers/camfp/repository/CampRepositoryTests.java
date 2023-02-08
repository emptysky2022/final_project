package com.campers.camfp.repository;

import java.util.List;
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
		sendQuery(type.CAMP);
	}

	public Object[] sendQuery(type camp) {
		
		Object[] data = null;

		switch (camp) {

		case CAMP:
			Camptest(campRepository);
			
			break;

		case REVIEW:

			break;

		case HISTORY:

			break;

		case CALANDER:

			break;

		default:
			break;

		}

		return data;
	}

	@Test
	public void Camptest(JpaRepository<?, ?> data) {

		List<?> test = data.findAll();
		//test

	}

	public enum type {
		CAMP, 
		REVIEW,
		HISTORY,
		CALANDER,
		CALANDER2
	}
	
	public enum query {
		SELECT, 
		SELECTALL,
		DELETE,
		DELETEALL
	}

}
