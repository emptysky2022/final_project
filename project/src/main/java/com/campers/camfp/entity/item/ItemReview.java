package com.campers.camfp.entity.item;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Value;

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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_ino", nullable = false)
	private Item item;
	
	private String capture;
	
	@Column(length=2000, nullable = false)
	private String content;
		
	@Column(length=50, nullable = false)
	private String reviewer;
	
	private int heart;
	
	private int star;
	
	public void increseHeart() {
		this.heart++;
	}
}
