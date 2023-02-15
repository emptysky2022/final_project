package com.campers.camfp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campers.camfp.dto.item.ItemReviewDTO;
import com.campers.camfp.service.item.ItemReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/review")
public class ItemReviewController {
	
	private final ItemReviewService itemReviewService;
	
	@GetMapping("/list/{ino}")
	public ResponseEntity<List<ItemReviewDTO>> getReviewOfItem(@PathVariable Long ino){
		List<ItemReviewDTO> result = itemReviewService.getReviewOfItem(ino);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
