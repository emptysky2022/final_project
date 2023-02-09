package com.campers.camfp.service;

import java.util.List;

import com.campers.camfp.dto.ShoppingCartDTO;
import com.campers.camfp.entity.Item;
import com.campers.camfp.entity.Member;
import com.campers.camfp.entity.ShoppingCart;

public interface ShoppingCartService {

	//장바구니 추가
	Long register(ShoppingCartDTO shoppingCartDTO);
	
	//사용자가 넣은 장바구니 목록 가져오기
	List<ShoppingCartDTO> getCartOfUser(String uid);
	
	//장바구니 수정
	void modify(ShoppingCartDTO shoppingCartDTO);
	
	//장바구니 삭제
	void remove(Long sno);
	
	//DTO를 entity로 변환
	default ShoppingCart dtoToEntity(ShoppingCartDTO shoppingCartDTO) {
		ShoppingCart shoppingCart = ShoppingCart.builder()
									.sno(shoppingCartDTO.getSno())
									.member(Member.builder().id(shoppingCartDTO.getUser()).build())
									.item(Item.builder().ino(shoppingCartDTO.getItem()).build())
									.build();
		
		return shoppingCart;
	}
	
	//entity를 DTO로 변환
	default ShoppingCartDTO entityToDto(ShoppingCart shoppingCart) {
		ShoppingCartDTO shoppingCartDTO = ShoppingCartDTO.builder()
				.sno(shoppingCart.getSno())
				.user(shoppingCart.getMember().getId())
				.item(shoppingCart.getItem().getIno())
				.build();
		
		return shoppingCartDTO;
	}
	
}
