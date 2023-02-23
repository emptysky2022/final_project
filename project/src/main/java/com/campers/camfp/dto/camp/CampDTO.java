package com.campers.camfp.dto.camp;

import java.util.ArrayList;
import java.util.List;

import com.campers.camfp.entity.camp.CampCalender;
import com.campers.camfp.entity.camp.CampReview;
import com.campers.camfp.entity.member.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampDTO {

	private Long cno;
	private Long mno;
	private String name;
	private String thumbnail;
	private String address;
	private String location;
	private String camptype;
	private String campintroduce;
	private int unit;
	private int count;
	private int star;
	private int heart;

	List<CampReview> campReview = new ArrayList<>();
	List<CampCalender> campCalendar = new ArrayList<>();

}
