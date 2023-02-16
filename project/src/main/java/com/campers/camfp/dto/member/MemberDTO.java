package com.campers.camfp.dto.member;

import com.campers.camfp.config.type.GenderType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
	
	private Long mno;
	private String id;
	private String pw;
	private String nickname;
	private String profileImg;
	private String name;
	private int age;
	private GenderType gender;
	private String address;
	private String phone;
	private String grade;
	private String introduce;

}