package com.campers.camfp.service.heart;

import java.util.List;

import com.campers.camfp.config.type.ProductType;
import com.campers.camfp.dto.heart.HeartDTO;
import com.campers.camfp.entity.heart.Heart;
import com.campers.camfp.entity.member.Member;

public interface HeartService {
	
	public HeartDTO findHeart(HeartDTO dto);
	
	public HeartDTO saveHeart(HeartDTO dto);
	
	public HeartDTO removeHeart(HeartDTO dto);
	
	public List<HeartDTO> getListOfProductType(Long mno, ProductType productType);
	
	default Heart dtoToEntity(HeartDTO heartDto) {
		Heart heart = Heart.builder().member(Member.builder().mno(heartDto.getMno()).build())
									 .hlno(heartDto.getHlno())
									 .productNum(heartDto.getProductNum())
									 .productType(heartDto.getProductType())
									 .build();
		
		return heart;
	}
	
	default HeartDTO entityToDto(Heart heart) {
		HeartDTO heartDto = HeartDTO.builder().mno(heart.getMember().getMno())
										 .hlno(heart.getHlno())
										 .productNum(heart.getProductNum())
										 .productType(heart.getProductType())
										 .build();
		
		return heartDto;
	}

}
