package com.campers.camfp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDTO {

	private Long ino;
	private String name;
	private String image;
	private String brand;
	private String maker;
	private String category1;
	private String category2;
	private int price;
	private String link;
	private int type;		//중고, 일반, 단종
	private int count;		//조회수
	private int star;		//별점
	private int heart;		//좋아요, 찜
}
