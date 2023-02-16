package com.campers.camfp.dto.board;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyDTO {

	private Long rno;
	private Long bno;
	private String replyer;
	private String content;
	private int heart;
	private LocalDateTime regDate;
	private LocalDateTime updateDate;
	
}
