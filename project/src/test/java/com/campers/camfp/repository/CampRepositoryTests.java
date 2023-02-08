package com.campers.camfp.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.campers.camfp.entity.Camp;
import com.campers.camfp.entity.CampCalender;
import com.campers.camfp.entity.CampHistory;
import com.campers.camfp.entity.CampReview;
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

	@Test
	public void test1() {
		findTable(type.CAMP);
	}
	
	@Test
	public void test() {
		for (int i  = 0; i< 6; i++) {
			
		createCampTest();
		createCampCalenderTest();
		createCampReviewTests();
		createCampReviewTests();
		createCampHistoryTests();
	}
	}
	
	@Test
	public void createCampTest() {
		Camp camp = Camp.builder().country("country").address("address").name("name").build();
		campRepository.save(camp);
	}
	
	
	@Test
	public void createCampCalenderTest() {
		LocalDateTime locDate1 = LocalDateTime.now();
		LocalDateTime locDate2 = LocalDateTime.now();
		
		CampCalender campCalender = CampCalender.builder().camp(getCampNum(1)).startdate(locDate1).endupDate(locDate2).build();
		campCalanderRepository.save(campCalender);
	}
	
	@Test
	public void createCampReviewTests() {
		
		CampReview campReview = CampReview.builder().image("img").content("con").nickname("nic").id("1").camp(getCampNum(1)).build();
		campReviewRepository.save(campReview);
		
	}
	
	@Test
	public void createCampHistoryTests() {
		short a = 1;
		CampHistory campHistory = CampHistory.builder().id("1").unit(1).state(a).camp(getCampNum(1)).build();
		campHistoryRepository.save(campHistory);
	}
	
	public Camp getCampNum(long data) {
		return Camp.builder().cno(3L).build();
	}
	
	@Test
	public void delteTest() {
		
		campReviewRepository.delete(CampReview.builder().camp(getCampNum(2)).nickname("").content("").build());
	}
	

	public Object[] findTable(type camp) {
		
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
