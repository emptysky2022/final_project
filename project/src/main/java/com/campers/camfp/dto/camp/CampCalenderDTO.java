package com.campers.camfp.dto.camp;


import java.time.LocalDateTime;

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
	private Camp camp;
	private LocalDateTime startdate;
	private LocalDateTime enddate;

}
