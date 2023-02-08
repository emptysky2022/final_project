package com.campers.camfp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemHistoryDTO {
	private Long ihno;
	//Member ID
	private String member;
	//Item number
	private Long item;
	private int amount;
	private byte state;
}
