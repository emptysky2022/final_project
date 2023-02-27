package com.campers.camfp.service.history;

import java.util.List;

import com.campers.camfp.config.type.HistoryType;
import com.campers.camfp.config.type.StateType;
import com.campers.camfp.dto.history.HistoryDTO;
import com.campers.camfp.entity.history.History;
import com.campers.camfp.entity.member.Member;

public interface HistoryService {
		
		//히스토리 등록
		Long register(HistoryDTO HistoryDTO);
		
		//카트에 있는 아이템 모두 등록
		void registerOfCart(List<Long> snos);

		//하나의 히스토리 가져오기
		HistoryDTO getOne(Long hno);

		//사용자의 모든 히스토리 가져오기
		List<HistoryDTO> getHistoryOfMember(Long mno);
		
		//히스토리 수정
		void modify(HistoryDTO HistoryDTO);
		
		//히스토리 삭제
		void remove(Long ihno);
		
		//DTO를 entity로 변환
		default History dtoToEntity(HistoryDTO historyDTO) {
			History history = History.builder()
										.hno(historyDTO.getHno())
										.member(Member.builder().mno(historyDTO.getMno()).build())
										.historyType(historyDTO.getHistoryType().toString())
										.historyNum(historyDTO.getHistoryNum())
										.state(historyDTO.getState().toString())
										.amount(historyDTO.getAmount())
										.startdate(historyDTO.getStartdate())
										.enddate(historyDTO.getEnddate())
										.address(historyDTO.getAddress())
										.peopleNum(historyDTO.getPeopleNum())
										.build();
			
			return history;
		}
		
		//entity를 DTO로 변환
		default HistoryDTO entityToDto(History history) {
			HistoryDTO historyDTO = HistoryDTO.builder()
					.hno(history.getHno())
					.mno(history.getMember().getMno())
					.historyType(HistoryType.valueOf(history.getHistoryType()))
					.historyNum(history.getHistoryNum())
					.regDate(history.getRegDate())
					.state(StateType.valueOf(history.getState()).getStatus())
					.amount(history.getAmount())
					.price(history.getPrice())
					.startdate(history.getStartdate())
					.enddate(history.getEnddate())
					.address(history.getAddress())
					.peopleNum(history.getPeopleNum())
					.build();
			
			return historyDTO;
		}

}
