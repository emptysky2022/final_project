package com.campers.camfp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.campers.camfp.dto.CampCalenderDTO;
import com.campers.camfp.dto.CampDTO;
import com.campers.camfp.dto.CampReviewDTO;
import com.campers.camfp.entity.Camp;
import com.campers.camfp.entity.CampCalender;
import com.campers.camfp.entity.CampReview;
import com.campers.camfp.entity.Member;
import com.campers.camfp.util.type.TableType;

public interface CampService {

	public Optional<Object> findbyId(TableType table, Long cno);

	public void remove(TableType table, Long cno);

	public Object modify(TableType table, Long cno);

	public Long register(TableType table, Object dto);
	
	public List<Object> findAll(TableType table, Long cno);
	
	/**
	 * @param table 변환할 table 명 table <br>
	 * @param dto 변환할 table 의 DataAccessObject <br>
	 * @return 해당 table Entity 를 Object 형식으로 리턴 
	 */
	default Object DTOToEntity(TableType table, Object dto) {
		Object value = null;

		switch (table) {

		case CAMP:
			
			CampDTO campDTO = (CampDTO) dto;
			Camp camp = Camp.builder().cno(campDTO.getCno())
									  .member(Member.builder().mno(campDTO.getMember().getMno()).build())
									  .name(campDTO.getName())
									  .thumbnail(campDTO.getThumbnail())
									  .country(campDTO.getCountry())
									  .address(campDTO.getAddress())
									  .unit(campDTO.getUnit())
									  .count(campDTO.getCount())
									  .star(campDTO.getStar())
									  .heart(campDTO.getHeart())
									  .build();
			value = camp;
			break;

		case CAMPREVIEW:
			
			CampReviewDTO reviewDTO = (CampReviewDTO) dto;
			CampReview campReview = CampReview.builder().crno(reviewDTO.getCrno())
														.camp(Camp.builder().cno(reviewDTO.getCamp().getCno()).build())
														.capture(reviewDTO.getCapture())
														.content(reviewDTO.getContent())
														.reviewer(reviewDTO.getReviewer())
														.heart(reviewDTO.getHeart())
														.build();
			
			value = campReview;
			break;

		case CAMPCALENDER:
			
			CampCalenderDTO calenderDTO = (CampCalenderDTO) dto;
			CampCalender campCalender = CampCalender.builder().ccno(calenderDTO.getCcno())
										.camp(Camp.builder().cno(calenderDTO.getCamp().getCno()).build())
										.startdate(calenderDTO.getStartdate())
										.enddate(calenderDTO.getEnddate())
										.build();
			value = campCalender;
			break;

		}

		return value;
	}

	/**
	 * @param table 변환할 table 명 table <br>
	 * @param dto 변환할 table 의 DataAccessObject <br>
	 * @retrun 해당 table DTO를 Object 형식으로 리턴 
	 * 
	 */
	default Object EntityToDTO(TableType table, Object entity) {
		Object value = null;

		switch (table) {

		case CAMP:
			
			Camp camp = (Camp) entity;
			CampDTO campDTO = CampDTO.builder().cno(camp.getCno())
					  						   .member(Member.builder().mno(camp.getMember().getMno()).build())
					  						   .name(camp.getName())
					  						   .thumbnail(camp.getThumbnail())
					  						   .country(camp.getCountry())
					  						   .address(camp.getAddress())
					  						   .unit(camp.getUnit())
					  						   .count(camp.getCount())
					  						   .star(camp.getStar())  
					  						   .heart(camp.getHeart())
					  						   .build();
			
			value = campDTO;
			break;

		case CAMPREVIEW:
			
			CampReview review = (CampReview) entity;
			CampReviewDTO campReview = CampReviewDTO.builder().crno(review.getCrno())
														.camp(Camp.builder().cno(review.getCamp().getCno()).build())
														.capture(review.getCapture())
														.content(review.getContent())
														.reviewer(review.getReviewer())
														.heart(review.getHeart())
														.build();
			
			value = campReview;
			break;

		case CAMPCALENDER:
			CampCalender calender = (CampCalender) entity;
			CampCalenderDTO campCalender = CampCalenderDTO.builder().ccno(calender.getCcno())
										.camp(Camp.builder().cno(calender.getCamp().getCno()).build())
										.startdate(calender.getStartdate())
										.enddate(calender.getEnddate())
										.build();
			value = campCalender;
			break;

		}

		return value;
	}

}
