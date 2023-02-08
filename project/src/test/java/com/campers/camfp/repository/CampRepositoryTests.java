package com.campers.camfp.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.campers.camfp.entity.Camp;
import com.campers.camfp.repository.CampRepository;

@SpringBootTest
public class CampRepositoryTests {

	@Autowired
	private CampRepository campRepository;
	
	@Test
	public void insertCamp() {
		
		IntStream.rangeClosed(0, 100).forEach(i -> {
			
			Camp camp = Camp.builder()
							.count((int)(Math.random()*100) + 1)
							.address("한국 " + i + "번지")
							.country("대한민국 " + i + "도")
							.build();
			campRepository.save(camp);
			
		});
	}
	
}
