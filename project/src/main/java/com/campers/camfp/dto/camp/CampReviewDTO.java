package com.campers.camfp.dto.camp;


import com.campers.camfp.entity.camp.Camp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampReviewDTO {

	private int crno;

	private Camp camp;
	private String capture;

	private String content;

	private String reviewer;

	private int heart;

}
