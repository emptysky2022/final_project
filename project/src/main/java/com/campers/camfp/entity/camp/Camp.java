package com.campers.camfp.entity.camp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.campers.camfp.config.type.CampingType;
import com.campers.camfp.entity.member.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="camp")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "member")
public class Camp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cno;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Member member;
	
	@Column(length = 30)
	private String name;
	
	@Column(length = 200)
	private String thumbnail;
	
	@Column(length = 30)
	private String location;

	@Column(length = 20)
	private String camptype;
	
	@Column(length = 500)
	private String campintroduce;
	
	@Column(length = 50)
	private String address;
	
	@Column(nullable = true)
	private int unit;

	@Column(nullable = true)
	private int count;
	
	@Column(nullable = true)
	private int star;

	@Column(nullable = true)
	private int heart;

}
