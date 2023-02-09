package com.campers.camfp.service;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.campers.camfp.dto.ItemHistoryDTO;
import com.campers.camfp.entity.Member;

@SpringBootTest
public class ItemHisrotyServiceTests {

	@Autowired
	private ItemHistoryService itemHistoryService;
	
	@Test
	public void insertDummies() {
		IntStream.rangeClosed(1, 50).forEach(i -> {
			ItemHistoryDTO itemHistoryDTO = ItemHistoryDTO.builder()
											.amount(1)
											.state((byte)1)
											.member("userID"+i)
											.item((long)i)
											.build();
			itemHistoryService.register(itemHistoryDTO);
		});
	}
	
	@Test
	public void modifyDummy() {
		ItemHistoryDTO itemHistoryDTO = itemHistoryService.getOne(2L);
		itemHistoryDTO.setState((byte)2);
		itemHistoryDTO.setAmount(2);
		itemHistoryService.modify(itemHistoryDTO);
	}
	
	@Test
	public void removeDummy() {
		itemHistoryService.remove(100L);
	}
}
