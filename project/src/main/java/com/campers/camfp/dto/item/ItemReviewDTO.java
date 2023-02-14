package com.campers.camfp.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemReviewDTO {
	private Long irno;
	//Item number
	private Long ino;
	//Image URL
	private String capture;
	private String content;
	//Member nickname
	private String reviewer;
	private int heart;
}
