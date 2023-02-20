package com.campers.camfp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.campers.camfp.repository.item.ItemReviewRepository;

@SpringBootTest
public class ItemReviewRepositoryTests {

	@Autowired
	private ItemReviewRepository itemReviewRepository;
	
	@Test
	public void deleteReviewDummy() {
		itemReviewRepository.deleteById(1L);
	}
}
