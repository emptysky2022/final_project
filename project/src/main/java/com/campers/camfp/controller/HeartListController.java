package com.campers.camfp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campers.camfp.service.member.HeartListService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/Heart")
@Log4j2
@RequiredArgsConstructor
public class HeartListController {
	
	private final HeartListService heartListService;

	

}
