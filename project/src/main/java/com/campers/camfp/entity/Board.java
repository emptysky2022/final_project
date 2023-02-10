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

import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Table(name="board")
@ToString(exclude = "member")
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor 
public class Board extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bno;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Member member;
	
	@Column(length = 30, nullable = false)
	private String title;
	
	@Column(length = 2000, nullable = false)
	private String content;
	

//	@Column(length = 10)
//	@ManyToOne(fetch = FetchType.LAZY)
//	private Member member;

	

	private String category; // 
	
	private int count; // 조회수
	
	private int heart; // 좋아요
	

//	@Column(nullable=false, columnDefinition="timestamp default now()", insertable=false)
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date regDate= new Date();
	

	
	// 시간은 예제처럼 BaseEntity를 따로 만들어서 관리를 하는게 좋을까여..?
	@Column(nullable=false, columnDefinition="timestamp default now()", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date regdate= new Date();
	
	@LastModifiedDate
	@Column(columnDefinition="timestamp default now()", insertable = false , updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate= new Date();

}
