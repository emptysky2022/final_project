package com.campers.camfp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campers.camfp.config.auth.PrincipalDetails;
import com.campers.camfp.config.type.HistoryType;
import com.campers.camfp.config.type.StateType;
import com.campers.camfp.dto.camp.CampCalenderDTO;
import com.campers.camfp.dto.history.HistoryDTO;
import com.campers.camfp.dto.item.ItemDTO;
import com.campers.camfp.dto.shopingcart.ShoppingCartDTO;
import com.campers.camfp.service.camp.CampService;
import com.campers.camfp.service.history.HistoryService;
import com.campers.camfp.service.item.ItemService;
import com.campers.camfp.service.shoppingcart.ShoppingCartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {
	
	private final HistoryService historyService;
	
	@GetMapping("/lists")
	public ResponseEntity<List<HistoryDTO>> getHistorys(@AuthenticationPrincipal PrincipalDetails principalDetails){
		List<HistoryDTO> result = historyService.getHistoryOfMember(principalDetails.getMember().getMno());
		
		log.info("결제내역 불러오기?");
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/item/list")
	public ResponseEntity<List<HistoryDTO>> getItemHistory(@AuthenticationPrincipal PrincipalDetails principalDetails){
		
		
		
		return null;
	}
	
	@GetMapping("/camp/list")
	public ResponseEntity<CampCalenderDTO> getCampHistory(){
		
		
		
		return null;
	}
	
	@PostMapping("/items")
	public ResponseEntity<String> registerItem(@RequestBody List<Long> snos, @AuthenticationPrincipal PrincipalDetails principalDetails){
		log.info("item history 등록");
		
		historyService.registerOfCart(snos);
		return new ResponseEntity<>("item success!", HttpStatus.OK);
	}
	
	@PostMapping("/camp")
	public ResponseEntity<CampCalenderDTO> registerCamp(){
		
		return null;
	}
	
}
