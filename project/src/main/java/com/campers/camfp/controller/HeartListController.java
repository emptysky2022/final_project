package com.campers.camfp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campers.camfp.config.auth.PrincipalDetails;
import com.campers.camfp.dto.heart.HeartDTO;
import com.campers.camfp.service.heart.HeartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/Heart")
@Log4j2
@RequiredArgsConstructor
public class HeartListController {
	
	private final HeartService heartListService;

	@PostMapping("/list")
	public ResponseEntity<Boolean> getHeart(@RequestBody HeartDTO dto, @AuthenticationPrincipal PrincipalDetails principalDetails){
		log.info(dto);
		dto.setMno(principalDetails.getMember().getMno());
		return null;
	}
	
	@PostMapping("/save")
	public ResponseEntity<Boolean> saveHeart(@RequestBody HeartDTO dto, @AuthenticationPrincipal PrincipalDetails principalDetails){
		log.info(dto);
		dto.setMno(principalDetails.getMember().getMno());
		return null;
	}
	
	@DeleteMapping("/remove")
	public ResponseEntity<Boolean> removeHeart(@RequestBody HeartDTO dto, @AuthenticationPrincipal PrincipalDetails principalDetails){
		log.info(dto);
		dto.setMno(principalDetails.getMember().getMno());
		return null;
	}
	

}
