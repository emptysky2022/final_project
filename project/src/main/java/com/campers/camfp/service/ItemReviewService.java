package com.campers.camfp.service;

import java.util.List;

import com.campers.camfp.dto.ItemReviewDTO;
import com.campers.camfp.entity.Item;
import com.campers.camfp.entity.ItemReview;
import com.campers.camfp.entity.User;

public interface ItemReviewService {
	
	//상품 리뷰 등록
	Long register(ItemReviewDTO itemReviewDTO);
	
	//상품에 대한 리뷰 가져오기
	List<ItemReviewDTO> getReviewOfItem(Long ino);
	
	//사용자가 작성한 리뷰 가져오기
	List<ItemReviewDTO> getReviewOfUser(String uid);
	
	//리뷰 수정하기
	void modify(ItemReviewDTO itemReviewDTO);
	
	//리뷰 삭제하기
	void remove(Long irno);
	
	//DTO를 entity로 변환
	default ItemReview dtoToEntity(ItemReviewDTO itemReviewDTO) {
		ItemReview itemReview = ItemReview.builder()
								.irno(itemReviewDTO.getIrno())
								.nickname(itemReviewDTO.getNickname())
								.image(itemReviewDTO.getImage())
								.content(itemReviewDTO.getContent())
								.item(Item.builder().ino(itemReviewDTO.getItem()).build())
								.user(User.builder().id(itemReviewDTO.getUser()).build())
								.build();
		
		return itemReview;
	}
	
	//entity를 DTO로 변환
	default ItemReviewDTO entityToDto(ItemReview itemReview) {
		ItemReviewDTO itemReviewDTO = ItemReviewDTO.builder()
								.irno(itemReview.getIrno())
								.nickname(itemReview.getNickname())
								.image(itemReview.getImage())
								.content(itemReview.getContent())
								.item(itemReview.getItem().getIno())
								.user(itemReview.getUser().getId())
								.build();
		
		return itemReviewDTO;
	}
}
