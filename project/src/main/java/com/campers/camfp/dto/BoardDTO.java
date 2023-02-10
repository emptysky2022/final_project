package com.campers.camfp.dto;

import java.time.LocalDateTime;

import com.campers.camfp.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardDTO {

	private Long bno;
	private String title;
	private String content;
	private Member member; // 이렇게 쓰는게 맞을까여
	private String category;
	private int count;
	private int heart;
	private LocalDateTime regDate;
	private LocalDateTime updateDate;
}
