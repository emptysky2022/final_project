package com.campers.camfp.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;

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
@ToString(exclude = "camp")
public class CampCalander {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ccno;
	
	// 지연로딩 설정
	// 조인 컬럼 설정
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "cno")
	private Camp camp;
	
	@Column(name="startDate")
	private LocalDateTime startdate;
	
	@Column(name="endupDate")
	private LocalDateTime endupDate;
	

	
	
}
