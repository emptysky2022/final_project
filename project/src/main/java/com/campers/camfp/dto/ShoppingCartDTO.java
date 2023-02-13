package com.campers.camfp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingCartDTO {
	private Long sno;
	//Member num
	private Long mno;
	//Item number
	private Long ino;
	private int amount;
}
