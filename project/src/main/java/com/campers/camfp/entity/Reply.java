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
@Getter
@Table(name="reply")
@ToString(exclude = "member")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reply extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rno;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Member member;
	
	@Column(nullable = false)
	private Long boardNum;

	@Column(nullable = false)
	private int boardType;
	
	@Column(length = 200)
	private String capture;

	@Column(length = 2000, nullable = false)
	private String content;
	
	private int heart;
	
}
