package com.campers.camfp.entity.item;

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
@Table(name="item_review")
@ToString(exclude = {"item"})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemReview extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long irno;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false ,cascade = CascadeType.REMOVE)
	private Item item;
	
	private String capture;
	
	@Column(length=2000, nullable = false)
	private String content;
		
	@Column(length=20, nullable = false)
	private String reviewer;
	
	private int heart;
}
