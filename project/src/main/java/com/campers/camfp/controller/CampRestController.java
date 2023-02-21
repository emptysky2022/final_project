package com.campers.camfp.controller;

import java.awt.PageAttributes.MediaType;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campers.camfp.config.type.CampingType;
import com.campers.camfp.config.type.TableType;
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

}
