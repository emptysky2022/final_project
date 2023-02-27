package com.campers.camfp.entity.heart;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.campers.camfp.config.type.GenderType;
import com.campers.camfp.config.type.ProductType;
import com.campers.camfp.entity.member.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="heart")
@ToString(exclude = "member")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Heart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hlno;
	
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Member member;
	
	@Column(nullable = false)
	private Long productNum;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ProductType productType;
	
}
