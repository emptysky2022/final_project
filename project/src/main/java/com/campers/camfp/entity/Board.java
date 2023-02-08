package com.campers.camfp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Table(name="board")
@ToString
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bno;
	
	@Column(nullable = false)
	private String title;
	
	@Column(length = 2000, nullable = false)
	private String content;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;
	
	private String category; // 
	
	private int  count; // 조회수
	
	private int heart; // 좋아요
	
	@Column(nullable=false, columnDefinition="timestamp default now()", insertable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDate= new Date();
}
