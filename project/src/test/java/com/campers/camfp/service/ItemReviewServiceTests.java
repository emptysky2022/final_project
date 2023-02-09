package com.campers.camfp.service;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.campers.camfp.dto.ItemReviewDTO;

@SpringBootTest
public class ItemReviewServiceTests {

	@Autowired
	private ItemReviewService itemReviewService;
	
	@Test
	public void insertDummies() {
		IntStream.rangeClosed(1, 50).forEach(i -> {
			ItemReviewDTO itemReviewDTO = ItemReviewDTO.builder()
											.item((long)i)
											.image("https://shopping-phinf.pstatic.net/main_8280118/82801183559.8.jpg")
											.content("정말 최고의 선택이에요")
											.member("userID"+i)
											.nickname("닉넴"+i)
											.build();

			itemReviewService.register(itemReviewDTO);
		});
	}
	
	@Test
	public void modifyDummy() {
		ItemReviewDTO itemReviewDTO = itemReviewService.getOne(2L);
		itemReviewDTO.setContent("정말 현명한 선택이에요");
		itemReviewDTO.setImage("https://shopping-phinf.pstatic.net/main_8473051/84730518441.4.jpg");
		itemReviewService.modify(itemReviewDTO);
	}
	
	@Test
	public void removeDummy() {
		itemReviewService.remove(1L);
	}
}
