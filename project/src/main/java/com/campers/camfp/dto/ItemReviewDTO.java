package com.campers.camfp.dto;

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
	private Long item;
	//Image URL
	private String image;
	private String content;
	//User ID
	private String user;
	private String nickname;
	private int heart;
}
