package com.campers.camfp.service;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.campers.camfp.config.type.TableType;
import com.campers.camfp.dto.camp.CampDTO;
import com.campers.camfp.dto.camp.CampReviewDTO;
import com.campers.camfp.entity.camp.Camp;
import com.campers.camfp.entity.camp.CampReview;
import com.campers.camfp.service.camp.CampService;

@SpringBootTest
public class CampServiceTests {
	
	@Autowired
	private CampService campService;
	
	@Test
	public void qu() {
		Object data = campService.findbyId(TableType.CAMP, 8L);
		
		System.out.println("여기까지 정상");
		
		if (data instanceof Camp) {
			System.out.println("나랑맞아");
		}
		
		Camp campdto = (Camp) data;
		System.out.println(data);
		CampDTO camp = (CampDTO) campService.EntityToDTO(TableType.CAMP, campdto);
		
		System.out.println(camp);
//		
	}
}
