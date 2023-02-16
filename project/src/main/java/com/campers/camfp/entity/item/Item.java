package com.campers.camfp.entity.item;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.campers.camfp.entity.member.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString(exclude = "member")
@Builder
@Getter
@Table(name="item")
@NoArgsConstructor
@AllArgsConstructor
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ino;				//기본키 AUTO_INCREMENT
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false ,cascade = CascadeType.REMOVE)
	private Member member;
	
	@Column(length = 200, nullable = false)
	private String name;			//상품의 이름
	
	private String thumbnail;		//상품 이미지
	
	@Column(length = 20)
	private String brand;			//상품 브랜드
	
	@Column(length = 30)
	private String maker;			//상품 메이커
	
	@Column(length = 10, nullable = false)
	private String category1;		//상품 카테고리 (소분류)
	//카테고리 목록 : 취사용품, 랜턴, 캠핑가구, 기타캠핑용품, 텐트, 캠핑매트, 아이스박스, 텐트/타프용품, 침낭, 타프, 천막, 캠핑왜건
	
	
	@Column(length = 10)
	private String category2;		//상품 카테고리 (세분류)
	
	@Column(nullable = false)
	private int price;				//상품 가격
	
	@Column(length = 200)
	private String link;			//상품 링크
	
	private int type;				//상품 타입(일반, 단종, 중고 등)
	
	private int count;				//조회수
	
	private int star;				//별점
	
	private int heart;				//좋아요, 찜 수
}
