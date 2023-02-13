package com.campers.camfp.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
public class CampReview extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int crno;

	// 지연로딩 설정
	// 조인 컬럼 설정
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "cno")
	private Camp camp;

	// Stirng 으로 구현하는게 맞는가 -> imageURL을 저장하기 때문에 String
	@Column(length = 200, nullable = true)
	private String capture;

	@Column(length = 200, nullable = false)
	private String content;

	@Column(length = 20, nullable = false)
	private String reviewer;

	private int heart;
}
