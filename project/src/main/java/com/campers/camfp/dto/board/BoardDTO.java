package com.campers.camfp.dto.board;

import java.time.LocalDateTime;

import com.campers.camfp.entity.member.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardDTO {

	private Long bno;
	private String title;
	private String content;
	private Long mno;
	private String nickname;
	private String category;
	private int replyCount;		// SH : 리플카운트가 없어서 추가함.
	private int count;
	private int heart;
	private LocalDateTime regDate;
	private LocalDateTime updateDate;
}
