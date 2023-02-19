package com.campers.camfp.dto.item;

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
	private Long mno;
	private String nickname;
	private String name;
	private String thumbnail;
	private String brand;
	private String maker;
	private String category1;
	private String category2;
	private int price;
	private String link;
	private int type;
	private int count;
	private int star;
	private int heart;
	
}
