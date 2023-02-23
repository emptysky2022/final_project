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

import com.campers.camfp.config.type.CampingType;
import com.campers.camfp.config.type.TableType;
import com.campers.camfp.dto.camp.CampCalenderDTO;
import com.campers.camfp.dto.camp.CampDTO;
import com.campers.camfp.dto.camp.CampReviewDTO;
import com.campers.camfp.entity.camp.CampCalender;
import com.campers.camfp.service.camp.CampService;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/camp")
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

		campService.findHeartOrCountRank(TableType.CAMP, 6, "별점순").forEach(result -> {
			dataList.add((CampDTO) result);
		});

		model.addAttribute("result", dataList);
		log.info("탔다");
	}

	@GetMapping("/campgroundsdetail")
	public void getGroundsDetail(Long cno, Model model) {
		
		List<CampCalenderDTO> calenderList = new ArrayList<>();

		CampDTO campdto = (CampDTO) campService.findbyId(TableType.CAMP, cno);
		
		// camp 조회수 늘리기
		campService.addData(TableType.CAMP, cno, "count");
		
		// camp data 가져오기
		String[] data = {"조회순"};
		campService.findDataOfCamp(TableType.CAMPCALENDER, cno, data).forEach(value ->{
			calenderList.add((CampCalenderDTO) value);
		});
		
		model.addAttribute("result", campdto);
		model.addAttribute("calender", calenderList);
		log.info(campdto);
		log.info("탔다");

	}
}
