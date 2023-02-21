package com.campers.camfp.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeartListDTO {
	
	private Long hlno;
	private Long productNum;
	private int productType;

}