package com.campers.camfp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="item_review")
@ToString(exclude = {"item", "member"})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemReview {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long irno;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Item item;
	
	private String image;
	
	@Column(length=2000, nullable = false)
	private String content;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;
	
	@Column(length=20, nullable = false)
	private String nickname;
	
	private int heart;
}
