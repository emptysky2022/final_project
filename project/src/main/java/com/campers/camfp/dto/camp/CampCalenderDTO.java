package com.campers.camfp.dto.camp;


import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.campers.camfp.entity.camp.Camp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampCalenderDTO {

	private int ccno;
	private Long cno;
	private String reservationer;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime startdate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime enddate;

}
