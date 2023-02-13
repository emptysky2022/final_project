package com.campers.camfp.entity.board;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.campers.camfp.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Table(name="reply")
@ToString(exclude = "board")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reply extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rno;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false ,cascade = CascadeType.REMOVE)
	private Board board;
	
	@Column(length = 20, nullable = false)
	private String replyer;

	@Column(length = 2000, nullable = false)
	private String content;
	
	private int heart;
	
	// ReplyServiceImpl에서 쓸건데....왜 써여
	public void changeContent(String content) {
		this.content = content;
	}
	
	public void changeHeart(int heart) {
		this.heart = heart;
	}
}
