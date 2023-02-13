package com.campers.camfp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Value;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name="shopping_cart")
@ToString(exclude = "member")
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sno;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Member member;
	
	@Column(nullable = false)
	private Long ino;
	
	@Value("1")
	private int amount;
}
