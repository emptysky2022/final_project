package com.campers.camfp.dto.heart;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.campers.camfp.config.type.ProductType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeartDTO {
	
	private Long hlno;
	private Long mno;
	private Long productNum;
	
	@Enumerated(EnumType.STRING)
	private ProductType productType;

}