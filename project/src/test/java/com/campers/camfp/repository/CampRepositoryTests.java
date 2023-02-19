package com.campers.camfp.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.campers.camfp.config.type.CampingType;
import com.campers.camfp.config.type.GenderType;
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
	public CampRepositoryTests(CampRepository a, CampReviewRepository b, HistoryRepository c, CampCalenderRepository d,
			MemberRepository e) {
		this.campRepository = a;
		this.campReviewRepository = b;
		this.historyRepository = c;
		this.campCalenderRepository = d;
		this.memberRepository = e;
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
	public void createCampTest() {
		for (Long i = 1L; i < 10; i++) {

			double dValue = Math.random();
			int iValue = (int) (dValue * 10) + 1;
	
			double dValuet = Math.random();
			int iValue2 = (int) (dValuet * 4);
			String addr = null;
			String location = null;
			CampingType campt = null;
			String imagepath = null;
			switch (iValue2) {
			case 0:
				campt = CampingType.COMMON;
				imagepath = "https://tse1.mm.bing.net/th?id=OIP.-n8Lawo9CI7OBeCxt0BF2QHaE8&pid=Api&P=0";
				addr = "경기도 포천시 영북면 산정호수로 529";
				location = "경기도";
				break;
			case 1:
				campt = CampingType.CARAVN;
				imagepath = "https://tse1.mm.bing.net/th?id=OIP.VtKQcloM_Z9uFWCrYoDS7gHaE7&pid=Api&P=0";
				addr = "충청남도 태안군 원북면 옥파로 1059-16";
				location = "충청도";

				break;
			case 2:
				campt = CampingType.GLAMPING;
				imagepath = "https://tse2.mm.bing.net/th?id=OIP.aeg1TGgqQIlZuTqbu6J4TgHaE8&pid=Api&P=0";
				addr = "강원도 속초시 장성천길 151";
				location = "강원도";

				break;
			case 3:
				campt = CampingType.CAR;
				imagepath = "https://tse1.mm.bing.net/th?id=OIP.E48xprIE4vwRdSAHnx3yswHaE8&pid=Api&P=0";
				addr = "경기도 연천군 전곡읍 선사로 270 ";
				location = "경기도";

				break;
			}

			Camp camp = Camp.builder().member(getmember(i)).location(location).address(addr).camptype(campt.getName())
					.name("name" + i).thumbnail(imagepath).heart(iValue).build();
			campRepository.save(camp);
		}
	}

	@Test
	public void createMemberTest() {
		for (int i = 0; i < 100; i++) {
			Member member = Member.builder().pw("pw").nickname("nickname" + i).id("id" + i).profileImg("img")
					.name("name").age(i).gender(GenderType.MALE)
					// .grade(true) ->String으로 바꿈
					.address("1").phone("1").introduce("1").build();

			memberRepository.save(member);

		}
	}

	@Test
	public void createCampCalenderTest() {
		
		IntStream.rangeClosed(1, 20).forEach(c -> {
			

		
		for (int i =100; i<104; i++) {
			
		

		double min = 1;
		double max = 28;
		int random = (int) ((Math.random() * (max - min)) + min);
		int random2 = (int) ((Math.random() * (12 - 1)) + min);
		System.out.println(random);
		
		LocalDateTime startdate = LocalDateTime.of(2023, random2, random, 10, 0);
		if (random > 26) {
			random = 2;
			random2 = random2 +1 ;
		}
		
		if (random2 == 13) {
			random2 = 1;
		}
		
		LocalDateTime enddate = LocalDateTime.of(2023, random2, random, 10, 0);

		CampCalender campCalender = CampCalender.builder().reservationer("장현수").camp(getCampNum(i)).startdate(startdate).enddate(enddate)
				.build();
		campCalenderRepository.save(campCalender);
		}
		});
	}

	@Test
	public void createCampReviewTests() {
		String img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSGlbH5s4tuIoRidTdu7GDm3psZcBkShHsW8g&usqp=CAU";
		String img2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRzobkOAJ6Z-IDoC0Io_xE69A_q6WuKInbnoQ&usqp=CAU";
		String img3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRNzQ0D8OIBoKGPDZua92hSi4Sw-pYbxdlDAA&usqp=CAU";
		String img4 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQU0xMuJkfJDu4OjZYSNG82AIYBVL8nPqRPJw&usqp=CAU";

		IntStream.rangeClosed(1, 4).forEach(value -> {		

		
		CampReview campReview = 
				CampReview.builder().capture(img4).content("댓글 테스트 중입니다 " + value)
				.reviewer("장현수" + value)
				.camp(getCampNum(101L))
				.heart(value)
				.build();
		campReviewRepository.save(campReview);
		
		});

	}

	@Test
	public void createHistoryTests() {
		byte a = 1;
		History history = History.builder().member(Member.builder().mno(1L).build()).amount(1).state(a).build();
		historyRepository.save(history);
	}

	public Camp getCampNum(long data) {
		return Camp.builder().cno(data).build();
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
			// value = Optional.of(campReviewRepository.findById(cno).get());
			break;

		case CAMPCALENDER:
			// value = Optional.of(campCalenderRepository.findById(cno).get());
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
