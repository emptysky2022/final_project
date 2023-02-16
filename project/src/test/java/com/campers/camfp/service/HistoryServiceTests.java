package com.campers.camfp.service;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.campers.camfp.dto.history.HistoryDTO;
import com.campers.camfp.service.history.HistoryService;

@SpringBootTest
public class HistoryServiceTests {

	@Autowired
	private HistoryService historyService;

	@Test
	public void insertDummies() {
		IntStream.rangeClosed(1, 50).forEach(i -> {
			HistoryDTO historyDTO = HistoryDTO.builder()
											.amount(1)
											.state((byte)1)
											.build();
			historyService.register(historyDTO);
		});
	}

	@Test
	public void modifyDummy() {
		HistoryDTO historyDTO = historyService.getOne(2L);
		historyDTO.setState((byte)2);
		historyDTO.setAmount(2);
		historyService.modify(historyDTO);
	}

	@Test
	public void removeDummy() {
		historyService.remove(100L);
	}
}