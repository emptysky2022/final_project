package com.campers.camfp.service.history;

import java.util.List;

import com.campers.camfp.dto.history.HistoryDTO;
import com.campers.camfp.entity.history.History;
import com.campers.camfp.entity.member.Member;

public interface HistoryService {
		
		//히스토리 등록
		Long register(HistoryDTO HistoryDTO);
		
		//하나의 히스토리 가져오기
		HistoryDTO getOne(Long hno);

		//사용자의 모든 히스토리 가져오기
		List<HistoryDTO> getHistoryOfUser(String mid);
		
		//히스토리 수정
		void modify(HistoryDTO HistoryDTO);
		
		//히스토리 삭제
		void remove(Long ihno);
		
		//DTO를 entity로 변환
		default History dtoToEntity(HistoryDTO HistoryDTO) {
			History history = History.builder()
										.hno(HistoryDTO.getHno())
										.member(Member.builder().mno(HistoryDTO.getMno()).build())
										//type, num 생성
										.amount(HistoryDTO.getAmount())
										.state(HistoryDTO.getState())
										.build();
			
			return history;
		}
		
		//entity를 DTO로 변환
		default HistoryDTO entityToDto(History history) {
			HistoryDTO historyDTO = HistoryDTO.builder()
					.hno(history.getHno())
					.mno(history.getMember().getMno())
					//type, num 생성
					.amount(history.getAmount())
					.state(history.getState())
					.build();
			
			return historyDTO;
		}
}
