package com.campers.camfp.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Camp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cno;
	
	@Column(length = 20)
	private String name;
	
	@Column(length = 20)
	private String country;
	
	@Column(length = 50)
	private String address;
	
	@Column(nullable = true)
	private int unit;

	@Column(nullable = true)
	private int count;
	
	@Column(nullable = true)
	private int star;
	
	@OneToMany(mappedBy = "camp")
	List<CampHistory> campHistory = new ArrayList<>();
	
	@OneToMany(mappedBy = "camp")
	List<CampReview> campReview = new ArrayList<>();
	
	@OneToMany(mappedBy = "camp")
	List<CampCalender> campCalendar = new ArrayList<>();
	

}
