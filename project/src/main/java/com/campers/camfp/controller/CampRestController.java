package com.campers.camfp.controller;

import java.awt.PageAttributes.MediaType;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campers.camfp.config.auth.PrincipalDetails;
import com.campers.camfp.config.type.CampingType;
import com.campers.camfp.config.type.TableType;
import com.campers.camfp.dto.camp.CampCalenderDTO;
import com.campers.camfp.dto.camp.CampDTO;
import com.campers.camfp.dto.camp.CampReviewDTO;
import com.campers.camfp.service.camp.CampService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/camp")
@Log4j2
@RequiredArgsConstructor
public class CampRestController {
	
	private final CampService campService;
	
	@GetMapping(value = "reply/{cno}")
	public ResponseEntity<List<CampReviewDTO>> getListByReview(@PathVariable("cno") Long cno){
		System.out.println("탔음");
		List<CampReviewDTO> reviews = new ArrayList<>();
		String[] datas = {"별점순"};
		campService.findDataOfCamp(TableType.CAMPREVIEW, cno, datas).forEach(value -> {
			reviews.add((CampReviewDTO) value);
		});
		
		log.info(reviews);
		
		return new ResponseEntity<> (reviews, HttpStatus.OK);
	}
	
	@GetMapping(value = "list/{type}/{locations}")
	public ResponseEntity<List<CampDTO>> getListByCamp(@PathVariable("type") String[] type, @PathVariable("locations") String[] locations){
		List<CampDTO> campdtoList = new ArrayList<>();
		System.out.println(locations);
		System.out.println(type);
		
		campdtoList = campService.findManayDataOfCamp(type, locations);
			
		log.info(campdtoList);
		return new ResponseEntity<> (campdtoList, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("review/register")
	public ResponseEntity<String> reviewRegister(@RequestBody CampReviewDTO dto, @AuthenticationPrincipal PrincipalDetails principalDetails){
		System.out.println("나탔따.");
		log.info(dto);
		String nickname = principalDetails.getMember().getNickname();
		
		if (nickname == null) {
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR);  
		}
		
		dto.setReviewer(principalDetails.getMember().getNickname());
		campService.register(TableType.CAMPREVIEW, dto);
		return new ResponseEntity(nickname, HttpStatus.OK);  
		
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("calendar/register")
	public ResponseEntity<String> calendarRegister(@RequestBody CampCalenderDTO dto, @AuthenticationPrincipal PrincipalDetails principalDetails){
		
		String nickname = principalDetails.getMember().getNickname();
		
		log.info("컨트롤러 데이터");
		log.info(dto);
		
		if (nickname == null) {
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR);  
		}
		
		dto.setReservationer(nickname);
		campService.register(TableType.CAMPCALENDER, dto);
		
		return new ResponseEntity("", HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/register")
	public ResponseEntity<String> campRegister(@RequestBody CampDTO dto, @AuthenticationPrincipal PrincipalDetails principalDetails){
		log.info(dto);
		
		Long mno = principalDetails.getMember().getMno();
		log.info(mno);
		
		if (mno == null) {
			return new ResponseEntity ("erro", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		dto.setMno(mno);
		campService.register(TableType.CAMP, dto);
		
		return new ResponseEntity(dto.getName(), HttpStatus.OK);
	}

}
