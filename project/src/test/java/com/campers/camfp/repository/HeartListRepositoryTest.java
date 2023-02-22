package com.campers.camfp.repository;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.campers.camfp.entity.heartlist.HeartList;
import com.campers.camfp.entity.member.Member;
import com.campers.camfp.repository.member.HeartListRepository;

@SpringBootTest
public class HeartListRepositoryTest {
	
	@Autowired
	private HeartListRepository heartListRepository;
	
	@Test
	public void insertHeart() {
		
		IntStream.rangeClosed(1, 50).forEach(i -> {
			
			Long mno = (long) ((Math.random() * 10) + 1);
			Long pkNum = (long) ((Math.random() * 50) + 1);
			int productType = (int) ((Math.random() * 3));
			
			HeartList heartList = HeartList.builder()
										   .member(Member.builder().mno(mno).build())
										   .productNum(pkNum)
										   .productType(productType)
										   .build();
			
			heartListRepository.save(heartList);
		});
	}
	
	@Test
	public void testGetList() {
		List<HeartList> heartList = heartListRepository.findAll();
		
		heartList.forEach(heart -> System.out.println(heart));
//		System.out.println(heartList);
	}

}
