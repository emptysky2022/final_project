package com.campers.camfp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/sample/")
public class CampController {

	@GetMapping("/camperproduct")
	public void getCamperProduct() {
		log.info("탔다");

	}

	@GetMapping("/campgrounds")
	public void getCampgrounds() {
		log.info("탔다");

	}

	@GetMapping("/campgroundsdetail")
	public void getGroundsDetail() {
		log.info("탔다");

	}
}
