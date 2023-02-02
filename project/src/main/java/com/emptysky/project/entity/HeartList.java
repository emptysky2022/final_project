package com.emptysky.project.entity;

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
@Table(name="heart_list")
@ToString(exclude = {"camp", "item", "user"})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeartList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hlno;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	private Camp camp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Item item;
}
