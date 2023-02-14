package com.campers.camfp.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.campers.camfp.config.type.TableType;
import com.campers.camfp.entity.camp.Camp;
import com.campers.camfp.entity.camp.CampCalender;
import com.campers.camfp.entity.camp.CampReview;
import com.campers.camfp.entity.history.History;
import com.campers.camfp.entity.member.Member;
import com.campers.camfp.repository.camp.CampCalenderRepository;
import com.campers.camfp.repository.camp.CampRepository;
import com.campers.camfp.repository.camp.CampReviewRepository;
import com.campers.camfp.repository.history.HistoryRepository;
import com.campers.camfp.repository.member.MemberRepository;

@SpringBootTest
public class CampRepositoryTests {

	// final 넣을지 말지 고민 필요.
	private CampRepository campRepository;
	private CampReviewRepository campReviewRepository;
	private HistoryRepository historyRepository;
	private CampCalenderRepository campCalenderRepository;
	private MemberRepository memberRepository;
	

	@Autowired
	public CampRepositoryTests(CampRepository a, CampReviewRepository b, HistoryRepository c,
			CampCalenderRepository d, MemberRepository e) {
		this.campRepository = a;
		this.campReviewRepository = b;
		this.historyRepository = c;
		this.campCalenderRepository = d;
		this.memberRepository = e ;
	}

	@Test
	public void test1() {
		findTable(type.CAMP);
	}

	@Test
	public void test() {
		for (int i = 2; i < 10; i++) {

			// createCampTest();
			createCampCalenderTest();
			createCampReviewTests();
			createHistoryTests();
		}
	}
	
	public Member getmember(Long mno) {
		return Member.builder().mno(mno + 1L).build(); 
	}

	@Test
	@Transactional
	public void createCampTest() {
		for (Long i = 0L; i < 10; i++) {
			Camp camp = Camp.builder().member(getmember(i))
					.country("country")
					.address("address")
					.name("name" + i)
					.thumbnail("thum")
					.build();
			campRepository.save(camp);
		}
	}
	
	@Test
	public void createMemberTest() {
		for (int i = 0; i < 10; i++) {
			Member member = Member.builder().pw("pw")
											.nickname("nickname" + i)
											.id("id"+i)
											.profileImg("img")
											.name("name")
											.age(i)
											.gender(true)
											//.grade(true) ->String으로 바꿈
											.address("1")
											.phone("1")
											.introduce("1")
											.build();
			
			memberRepository.save(member);

		}
	}

	@Test
	public void createCampCalenderTest() {
		LocalDateTime locDate1 = LocalDateTime.now();
		LocalDateTime locDate2 = LocalDateTime.now();

		CampCalender campCalender = CampCalender.builder().camp(getCampNum(1)).startdate(locDate1).enddate(locDate2)
				.build();
		campCalenderRepository.save(campCalender);
	}

	@Test
	public void createCampReviewTests() {

//		CampReview campReview = CampReview.builder().capture("img").content("con").reviewer("nic").camp(getCampNum(Integer.toUnsignedLong(i)))
//				.build();
//		campReviewRepository.save(campReview);

	}

	@Test
	public void createHistoryTests() {
		byte a = 1;
		History history = History.builder().member(Member.builder().mno(1L).build()).amount(1).state(a).build();
		historyRepository.save(history);
	}

	public Camp getCampNum(long data) {
		return Camp.builder().cno(3L).build();
	}

	@Test
	public void delteTest() {

		campReviewRepository.delete(CampReview.builder().camp(getCampNum(2)).reviewer("").content("").build());
	}

	@Test
	@Transactional
	public Object serviceTetst() {
		Optional<Object> value = null;
		
		

		TableType table = TableType.CAMP;
		Long cno = 1l;

		switch (table) {

		case CAMP:
			
			value = Optional.of(campRepository.findById(cno).get());
			
			break;

		case CAMPREVIEW:
			//value = Optional.of(campReviewRepository.findById(cno).get());
			break;

		case CAMPCALENDER:
			//value = Optional.of(campCalenderRepository.findById(cno).get());
			break;

		default:
			System.out.println("Not Found Type : " + table);
			break;
		}
		System.out.println("최종결과 : " + value);

		if (value.isPresent()) {
			System.out.println("Value 값이 없습니다.");
		}

		System.out.println("최종결과 : " + value);

		return value.get();
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
		CAMP, REVIEW, HISTORY, CALANDER, CALANDER2
	}

	public enum query {
		SELECT, SELECTALL, DELETE, DELETEALL
	}

}
