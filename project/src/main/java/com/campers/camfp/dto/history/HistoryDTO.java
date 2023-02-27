package com.campers.camfp.dto.history;

import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.format.annotation.DateTimeFormat;

import com.campers.camfp.config.type.HistoryType;
import com.campers.camfp.config.type.StateType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryDTO {
	private Long hno;
	//Member ID
	private Long mno;
	//history type(item, camp)
	@Enumerated(EnumType.STRING)
	private HistoryType historyType;
	//history number
	private Long historyNum;
	//item/camp 이름
	private String name; 
	//state(결제 완료, 배송준비중, 배송중, 배송완료)
	private String state;
	
	private int price;
	
	private LocalDateTime regDate;
	
	//item일때
	private int amount;
	
	
	//camp일때
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime startdate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime enddate;
	private String address;
	private int peopleNum;
	
}
