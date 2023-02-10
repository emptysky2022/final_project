package com.campers.camfp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campers.camfp.entity.Camp;
import com.campers.camfp.entity.CampCalender;
import com.campers.camfp.entity.CampReview;
import com.campers.camfp.repository.CampCalenderRepository;
import com.campers.camfp.repository.CampRepository;
import com.campers.camfp.repository.CampReviewRepository;
import com.campers.camfp.util.type.TableType;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
//@RequiredArgsConstructor
public class CampServiceImpl implements CampService {

	private final CampRepository campRepository;
	private final CampReviewRepository campReviewRepository;
	private final CampCalenderRepository campCalenderRepository;

	@Autowired
	public CampServiceImpl(CampRepository camp, CampReviewRepository review, CampCalenderRepository calender) {
		this.campRepository = camp;
		this.campReviewRepository = review;
		this.campCalenderRepository = calender;
	}

	@Override
	public Optional<? super Object> findbyId(TableType table, Long pk) {

		Optional<?> value = null;

		switch (table) {

		case CAMP:
			value = campRepository.findById(pk);
			break;

		case CAMPREVIEW:
			value = campReviewRepository.findById(pk);
			break;

		case CAMPCALENDER:
			value = campCalenderRepository.findById(pk);
			break;

		default:

			System.out.println("Not Found Type : " + table);

			break;
		}

		if (value.isPresent()) {
			System.out.println("Value 값이 없습니다.");
		}

		return null;
	}

	@Override
	public void remove(TableType table, Long cno) {

		switch (table) {

		case CAMP:
			campRepository.deleteById(cno);
			break;

		case CAMPREVIEW:
			campReviewRepository.deleteById(cno);
			break;

		case CAMPCALENDER:
			campCalenderRepository.deleteById(cno);
			break;

		default:

			System.out.println("Not Found Type : " + table);

			break;
		}

	}

	@Override
	public Long register(TableType table, Object dto) {
		
		Object result;
		
		switch (table) {

		case CAMP:
			Camp camp = (Camp)dto;
			result = campRepository.save(camp);
			break;

		case CAMPREVIEW:
			CampReview campReview = (CampReview)dto;
			result = campReviewRepository.save(campReview);
			break;

		case CAMPCALENDER:
			CampCalender campCalender = (CampCalender)dto;
			result = campCalenderRepository.save(campCalender);
			break;

		default:

			System.out.println("Not Found Type : " + table);

			break;
		}
		
		return null;
	}
	
	
	@Override
	public Object modify(TableType table, Long cno) {

		return null;
	}
	 

}
