package com.campers.camfp.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="member")
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	
	//사용자 ID
	@Id
	@Column(length = 20)
	private String id;
	
	//사용자 패스워드
	@Column(length = 30, nullable = false)
	private String pass;
	
	//사용자 닉네임
	@Column(length = 10, nullable = false, unique = true)
	private String nickname;
	
	//사용자 프로필 사진
	private String image;
	
	//사용자 이름
	@Column(length = 20)
	private String name; 
	
	//사용자 나이
	private int age;
	
	//사용자 성별
	private Boolean gender;
	
	//사용자 주소
	@Column(length = 50)
	private String address;
	
	//사용자 전화번호
	@Column(length = 15)
	private String phone;
	
	//사용자 권한(회원 / 관리자)
	private Boolean grade;
	
	//사용자 소개
	@Column(length = 1000)
	private String introduce;
	
	@OneToMany(mappedBy = "member", orphanRemoval = true)
	List<CampReview> campReview = new ArrayList<>();
	
	@OneToMany(mappedBy = "member", orphanRemoval = true)
	List<CampHistory> campHistrie = new ArrayList<>();
	

	public void change(String pass, String nickname, String image, String name, int age, String address,
			String phone, String introduce) {
		
		this.pass  = pass;
		this.nickname = nickname;
		this.image = image;
		this.name = name;
		this.age = age;
		this.address = address;
		this.phone = phone;
		this.introduce = introduce;
	}
	
	
	
}
