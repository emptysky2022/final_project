package com.campers.camfp.entity.camp;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.bind.annotation.DeleteMapping;

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
public class CampCalender {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ccno;
	
	// 지연로딩 설정
	// 조인 컬럼 설정
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "cno")
	private Camp camp;
	
	@Column(length = 50, nullable = false)
	private String reservationer;
	
	@Column(name="startdate")
	private LocalDateTime startdate;
	
	@Column(name="enddate")
	private LocalDateTime enddate;
	
}
