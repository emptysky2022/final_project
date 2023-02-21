package com.campers.camfp.entity.board;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.campers.camfp.entity.base.BaseEntity;
import com.campers.camfp.entity.member.Member;

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
public class Board extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bno;
	
	@Column(length = 30, nullable = false)
	private String title;
	
	@Column(length = 2000, nullable = false)
	private String content;
  
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="mno")
	private Member member;

	private String category;
	
	private int count; // 조회수
	
	private int heart; // 좋아요
	
	public void change(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
	public void increseCount() {
		this.count++;
	}
	
}