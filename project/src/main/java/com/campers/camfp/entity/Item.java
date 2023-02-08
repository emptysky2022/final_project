package com.campers.camfp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@Builder
@Getter
@Table(name="item")
@NoArgsConstructor
@AllArgsConstructor
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ino;
	
	@Column(length = 200, nullable = false)
	private String name;
	
	private String image;
	
	@Column(length = 20)
	private String brand;
	
	@Column(length = 30)
	private String maker;
	
	private String category1;
	
	private String category2;
	
	@Column(nullable = false)
	private int price;
	
	private String link;
	
	private int type;
	
	private int count;
	
	private int star;
	
	private int heart;
}
