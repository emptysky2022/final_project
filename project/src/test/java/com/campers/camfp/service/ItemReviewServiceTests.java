package com.campers.camfp.service;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.campers.camfp.dto.item.ItemReviewDTO;
import com.campers.camfp.service.item.ItemReviewService;

@SpringBootTest
public class ItemReviewServiceTests {

	@Autowired
	private ItemReviewService itemReviewService;

	@Test
	public void insertDummies() {
		IntStream.rangeClosed(1, 50).forEach(i -> {
			ItemReviewDTO itemReviewDTO = ItemReviewDTO.builder()
											.ino((long)i)
											.capture("https://shopping-phinf.pstatic.net/main_8280118/82801183559.8.jpg")
											.content("정말 최고의 선택이에요")
											.reviewer("코코도치")
											.build();

			itemReviewService.register(itemReviewDTO);
		});
	}

	@Test
	public void modifyDummy() {
		ItemReviewDTO itemReviewDTO = itemReviewService.getOne(2L);
		itemReviewDTO.setContent("정말 현명한 선택이에요");
		itemReviewDTO.setCapture("https://shopping-phinf.pstatic.net/main_8473051/84730518441.4.jpg");
		itemReviewService.modify(itemReviewDTO);
	}

	@Test
	public void removeDummy() {
		itemReviewService.remove(1L);
	}
}