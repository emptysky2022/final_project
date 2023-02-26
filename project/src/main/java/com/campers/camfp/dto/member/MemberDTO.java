package com.campers.camfp.dto.member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
	@NotBlank(message="아이디는 필수로 입력해야 합니다.")
	@Pattern(regexp = "^[a-z0-9]{6,20}$", message = "아이디는 영어 소문자와 숫자만 사용하여 6~20자리여야 합니다.")
	private String id;
	@NotBlank(message="비밀번호는 필수로 입력해야 합니다.")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "비밀번호는 문자+숫자+하나의 특수문자 사용하여 8~20자리여야 합니다.")
	private String pw;
	@NotBlank(message = "닉네임은 필수 입력값입니다.")
	@Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$" , message = "닉네임은 특수문자를 포함하지 않은 2~10자리여야 합니다.")
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
	
	
	