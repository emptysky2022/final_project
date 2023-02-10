package com.campers.camfp.dto;

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
	private String pw;	// pw로 바꿨으면 함.
	private String nickname;
	private String profileImg;
	private String name;
	private int age;
	private Boolean gender;
	private String address;
	private String phone;
	private Boolean grade;
	private String introduce;

}