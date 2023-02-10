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
@Table(name="heart_list")
@ToString(exclude = "member")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeartList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hlno;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Member member;
	
	@Column(nullable = false)
	private Long productNum;
	
	@Column(nullable = false)
	private int productType;
	
}
