package com.campers.camfp.entity;

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
@ToString(exclude = {"user", "item"})
@Table(name="item_history")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ihno;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Item item;
	
	private int amount;
	
	private byte state;
}
