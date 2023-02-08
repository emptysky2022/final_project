package com.campers.camfp.service;

import java.util.List;

import com.campers.camfp.dto.ItemHistoryDTO;
import com.campers.camfp.entity.Item;
import com.campers.camfp.entity.ItemHistory;
import com.campers.camfp.entity.Member;
import com.campers.camfp.entity.Member;

public interface ItemHistoryService {

	//상품 히스토리 등록
	Long register(ItemHistoryDTO itemHistoryDTO);
	
	//사용자의 모든 상품 히스토리 가져오기
	List<ItemHistoryDTO> getHistoryOfUser(String mid);
	
	//상품 히스토리 수정
	void modify(ItemHistoryDTO itemHistoryDTO);
	
	//상품 히스토리 삭제
	void remove(Long ihno);
	
	//DTO를 entity로 변환
	default ItemHistory dtoToEntity(ItemHistoryDTO itemHistoryDTO) {
		ItemHistory itemHistory = ItemHistory.builder()
									.ihno(itemHistoryDTO.getIhno())
									.member(Member.builder().id(itemHistoryDTO.getMember()).build())
									.item(Item.builder().ino(itemHistoryDTO.getItem()).build())
									.amount(itemHistoryDTO.getAmount())
									.state(itemHistoryDTO.getState())
									.build();
		
		return itemHistory;
	}
	
	//entity를 DTO로 변환
	default ItemHistoryDTO entityToDto(ItemHistory itemHistory) {
		ItemHistoryDTO itemHistoryDTO = ItemHistoryDTO.builder()
				.ihno(itemHistory.getIhno())
				.member(itemHistory.getMember().getId())
				.item(itemHistory.getItem().getIno())
				.amount(itemHistory.getAmount())
				.state(itemHistory.getState())
				.build();
		
		return itemHistoryDTO;
	}
	
}
