package com.campers.camfp.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.persistence.Column;
import javax.persistence.Id;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.campers.camfp.entity.Camp;
import com.campers.camfp.entity.CampCalender;
import com.campers.camfp.entity.CampHistory;
import com.campers.camfp.entity.CampReview;
import com.campers.camfp.entity.Member;
import com.campers.camfp.repository.CampRepository;

@SpringBootTest
public class CampRepositoryTests {

	private final CampRepository campRepository;
	private final CampReviewRepository campReviewRepository;
	private final CampHistoryRepository campHistoryRepository;
	private final CampCalenderRepository campCalanderRepository;
	private final MemberRepository memberRepository;

	@Autowired
	public CampRepositoryTests(CampRepository a, CampReviewRepository b, CampHistoryRepository c,
			CampCalenderRepository d, MemberRepository e) {
		this.campRepository = a;
		this.campReviewRepository = b;
		this.campHistoryRepository = c;
		this.campCalanderRepository = d;
		this.memberRepository = e;
	}

	@Test
	public void test1() {
		findTable(type.CAMP);
	}

	@Test
	public void test() {
		for (int i = 0; i < 11; i++) {

			createCampTest();
//			createCampCalenderTest();
		//	createCampReviewTests();
		//	createCampHistoryTests();
		}
	}

	public Camp getCampNum(long data) {
		return Camp.builder().cno(data).build();
	}

	@Test
	public void delteTest() {

		campRepository.delete(getCampNum(1));
	}

	@Test
	public void createCampTest() {
		for (int i = 0 ; i<10; i++) {
		Camp camp = Camp.builder().country("country").address("address").name("name").build();
		campRepository.save(camp);
		}
	}

	@Test
	public void creatmemberTest() {

		for (int i = 0 ; i<10; i++) {
			
		Member member = Member.builder().id("ide" + i).pass("pw").age(1).grade(true).introduce("1").gender(true).name("")
				.nickname("a" + i).build();
		
		memberRepository.save(member);
		}
	}

	@Test
	public void createCampCalenderTest() {
		
		LocalDateTime locDate1 = LocalDateTime.now();
		LocalDateTime locDate2 = LocalDateTime.now();

		CampCalender campCalender = CampCalender.builder().camp(getCampNum(1)).startdate(locDate1).endupDate(locDate2)
				.build();
		campCalanderRepository.save(campCalender);
	}

	@Test
	public void createCampReviewTests() {
		for (int i = 0 ; i<5; i++) {
			
		Member member = Member.builder()
				  .id("id" + i)
				  .pass("d")
				  .nickname("1" + i)
				  .age(1)
				  .build();

		CampReview campReview = CampReview.builder().image("img")
													.content("con")
													.nickname("1" + i)
													.camp(getCampNum(i+1))
				.member(member)
				.build();
		campReviewRepository.save(campReview);
		
		}

	}

	@Test
	public void createCampHistoryTests() {
		List<Object> data = findTable(type.CAMP);
	}

	public List<Object> findTable(type camp) {

		List<Object> data = null;

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
		int a = 1;
		
	}

	public enum type {
		CAMP, REVIEW, HISTORY, CALANDER, CALANDER2
	}
	// 추상클래스

	public enum query {
		SELECT, SELECTALL, DELETE, DELETEALL
	}

}
