package com.campers.camfp.service.shoppingcart;

import java.util.List;

import com.campers.camfp.dto.shopingcart.ShoppingCartDTO;
import com.campers.camfp.entity.member.Member;
import com.campers.camfp.entity.shoppingcart.ShoppingCart;

public interface ShoppingCartService {

	//장바구니 추가
	Long register(ShoppingCartDTO shoppingCartDTO);
	
	//상품 장바구니 하나 가져오기
	ShoppingCartDTO getOne(Long sno);
	
	//사용자가 넣은 장바구니 목록 가져오기
	List<ShoppingCartDTO> getCartOfMember(String mid);
	
	//장바구니 수정
	void modify(ShoppingCartDTO shoppingCartDTO);
	
	//장바구니 삭제
	void remove(Long sno);
	
	//DTO를 entity로 변환
	default ShoppingCart dtoToEntity(ShoppingCartDTO shoppingCartDTO) {
		ShoppingCart shoppingCart = ShoppingCart.builder()
									.sno(shoppingCartDTO.getSno())
									.member(Member.builder().mno(shoppingCartDTO.getMno()).build())
									.ino(shoppingCartDTO.getIno())
									.build();
		
		return shoppingCart;
	}
	
	//entity를 DTO로 변환
	default ShoppingCartDTO entityToDto(ShoppingCart shoppingCart) {
		ShoppingCartDTO shoppingCartDTO = ShoppingCartDTO.builder()
				.sno(shoppingCart.getSno())
				.mno(shoppingCart.getMember().getMno())
				.ino(shoppingCart.getIno())
				.build();
		
		return shoppingCartDTO;
	}
	
}
