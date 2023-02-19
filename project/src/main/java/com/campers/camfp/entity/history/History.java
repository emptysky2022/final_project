package com.campers.camfp.entity.history;

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
@Table(name="history")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "member")
public class History {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hno;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false ,cascade = CascadeType.REMOVE)
	private Member member;
	
	@Column(nullable = false)
	private Long historyNum;

	@Column(nullable = false)
	private int historyType;
	
	private int amount;
	
	private byte state;
	
	// 지연로딩 설정
	// 조인 컬럼 설정
//	@OnDelete(action = OnDeleteAction.CASCADE)
//	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.REMOVE)
//	@JoinColumn(name = "cno")
//	private Camp camp;
	
}