package com.campers.camfp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@Builder
@Getter
@Table(name="item")
@NoArgsConstructor
@AllArgsConstructor
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ino;				//기본키 AUTO_INCREMENT
	
	@Column(length = 200, nullable = false)
	private String name;			//상품의 이름
	
	private String image;			//상품 이미지
	
	@Column(length = 20)
	private String brand;			//상품 브랜드
	
	@Column(length = 30)
	private String maker;			//상품 메이커
	
	private String category1;		//상품 카테고리 (소분류)
	
	private String category2;		//상품 카테고리 (세분류)
	
	@Column(nullable = false)
	private int price;				//상품 가격
	
	private String link;			//상품 링크
	
	private int type;				//상품 타입(일반, 단종, 중고 등)
	
	private int count;				//조회수
	
	private int star;				//별점
	
	private int heart;				//좋아요, 찜 수
}
