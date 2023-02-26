package com.campers.camfp.repository;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.campers.camfp.config.type.ProductType;
import com.campers.camfp.entity.heart.Heart;
import com.campers.camfp.entity.member.Member;
import com.campers.camfp.repository.heart.HeartListRepository;

@SpringBootTest
public class HeartListRepositoryTest {
	
	@Autowired
	private HeartListRepository heartListRepository;
	
	@Test
	public void insertHeart() {
		
		IntStream.rangeClosed(1, 2).forEach(i -> {
			
			
			Heart heartList = Heart.builder()
										   .member(Member.builder().mno(1L).build())
										   .productNum(1l)
										   .productType(ProductType.BOARD)
										   .build();
			
			heartListRepository.save(heartList);
		});
	}
	
	@Test
	public void testGetList() {
		List<Heart> heartList = heartListRepository.findAll();
		
		heartList.forEach(heart -> System.out.println(heart));
//		System.out.println(heartList);
	}

}
