package com.campers.camfp.service.item;

import java.util.List;

import com.campers.camfp.dto.item.ItemReviewDTO;
import com.campers.camfp.entity.item.Item;
import com.campers.camfp.entity.item.ItemReview;

public interface ItemReviewService {
	
	//상품 리뷰 등록
	Long register(ItemReviewDTO itemReviewDTO);
	
	//상품 리뷰 하나 가져오기
	ItemReviewDTO getOne(Long irno);
	
	//상품에 대한 리뷰 가져오기
	List<ItemReviewDTO> getReviewOfItem(Long ino);
	
	//사용자가 작성한 리뷰 가져오기
	List<ItemReviewDTO> getReviewOfMember(String mid);
	
	//리뷰 수정하기
	void modify(ItemReviewDTO itemReviewDTO);
	
	//리뷰 삭제하기
	void remove(Long irno);
	
	//DTO를 entity로 변환
	default ItemReview dtoToEntity(ItemReviewDTO itemReviewDTO) {
		ItemReview itemReview = ItemReview.builder()
								.irno(itemReviewDTO.getIrno())
								.reviewer(itemReviewDTO.getReviewer())
								.capture(itemReviewDTO.getCapture())
								.content(itemReviewDTO.getContent())
								.item(Item.builder().ino(itemReviewDTO.getIno()).build())
								.build();
		
		return itemReview;
	}
	
	//entity를 DTO로 변환
	default ItemReviewDTO entityToDto(ItemReview itemReview) {
		ItemReviewDTO itemReviewDTO = ItemReviewDTO.builder()
								.irno(itemReview.getIrno())
								.ino(itemReview.getItem().getIno())
								.reviewer(itemReview.getReviewer())
								.capture(itemReview.getCapture())
								.content(itemReview.getContent())
								.build();
		
		return itemReviewDTO;
	}
}
