package com.campers.camfp.dto.history;

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
	//Item number
	private Long historyNum;
	private int historyType;
	private int amount;
	private byte state;
}
