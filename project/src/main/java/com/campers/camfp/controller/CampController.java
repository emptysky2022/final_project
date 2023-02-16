package com.campers.camfp.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.campers.camfp.config.type.TableType;
import com.campers.camfp.dto.camp.CampDTO;
import com.campers.camfp.service.camp.CampService;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/sample")
public class CampController {

	@Autowired
	CampService campService;

	@GetMapping("/camperproduct")
	public void getCamperProduct() {
		log.info("탔다");

	}

	@GetMapping("/campgrounds")
	public void getCampgrounds(Model model) {
		List<CampDTO> dataList = new ArrayList<>();

		campService.findHeartRank(TableType.CAMP, 5).forEach(result -> {
			dataList.add((CampDTO) result);
		});

		dataList.forEach(test -> {
			System.out.println(test);
		});

		model.addAttribute("result", dataList);
		log.info("탔다");
	}

	@GetMapping("/campgroundsdetail")
	public void getGroundsDetail(Long cno, Model model) {
		
		CampDTO campdto = (CampDTO) campService.findbyId(TableType.CAMP, cno);
		
		model.addAttribute("result", campdto);
		log.info("탔다");

	}
}
