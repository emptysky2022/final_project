package com.campers.camfp.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "camp")
public class CampReview {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int crno;

	// 지연로딩 설정
	// 조인 컬럼 설정
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "cno")
	private Camp camp;

	// Stirng 으로 구현하는게 맞는가
	@Column(length = 200, nullable = true)
	private String image;

	@Column(length = 200, nullable = false)
	private String content;

	private int id;

	@Column(length = 20, nullable = false)
	private String nickname;


}
