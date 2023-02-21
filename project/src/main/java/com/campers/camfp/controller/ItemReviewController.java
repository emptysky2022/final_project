package com.campers.camfp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.campers.camfp.config.auth.PrincipalDetails;
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
	
	@GetMapping("/detail/{ino}")
	public ResponseEntity<List<Object>> getReviewOfItem(@PathVariable Long ino, @AuthenticationPrincipal PrincipalDetails principalDetails){
		List<ItemReviewDTO> result = itemReviewService.getReviewOfItem(ino);
		if(principalDetails != null) {
			return new ResponseEntity<>(List.of(result, principalDetails.getMember()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(List.of(result), HttpStatus.OK);
		}
	}
	
	@GetMapping("/heart/{irno}")
	public ResponseEntity<Integer> clickHeart(@PathVariable Long irno){
		log.info("click item review heart");
		int result = itemReviewService.heartOfMember(irno);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Long> registerReviewOfItem(@RequestBody ItemReviewDTO itemReviewDTO, @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception{
		log.info("register controller dto : " + itemReviewDTO);
		itemReviewDTO.setReviewer(principalDetails.getMember().getNickname());
		itemReviewService.register(itemReviewDTO);
		
		return new ResponseEntity<>(itemReviewDTO.getIno(), HttpStatus.OK);
	}
	
}
