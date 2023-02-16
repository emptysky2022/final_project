package com.campers.camfp.entity.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.campers.camfp.config.type.GenderType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="member")
@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	
	//사용자 number
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mno;
	
	@Column(length =50, unique = true, nullable = false)
	private String id;
	
	//사용자 패스워드
	@Column(length = 100, nullable = false)
	private String pw;
	
	//사용자 닉네임
	@Column(length = 10, nullable = false, unique = true)
	private String nickname;
	
	//사용자 프로필 사진
	private String profileImg;
	
	//사용자 이름
	@Column(length = 20)
	private String name;
	
	//사용자 나이
	@Column(nullable=true)
	private int age;
	
	//사용자 성별
	private GenderType gender;
	
	//사용자 주소
	@Column(length = 50)
	private String address;
	
	//사용자 전화번호
	@Column(length = 15)
	private String phone;
	
	//사용자 권한(회원 / 관리자)
	private String grade;
	
	//사용자 소개
	@Column(length = 1000)
	private String introduce;

	public void change(String pw, String nickname, String profileImg, String name, int age, String address,
			String phone, String introduce) {
		
		this.pw  = pw;
		this.nickname = nickname;
		this.profileImg = profileImg;
		this.name = name;
		this.age = age;
		this.address = address;
		this.phone = phone;
		this.introduce = introduce;
	}

	public Member(String id, String pw, String nickname, String profileImg, String name, int age,
			GenderType gender, String address, String phone, String grade, String introduce) {
		this.id = id;
		this.pw = pw;
		this.nickname = nickname;
		this.profileImg = profileImg;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.address = address;
		this.phone = phone;
		this.grade = grade;
		this.introduce = introduce;
	}


	
	
	
}
