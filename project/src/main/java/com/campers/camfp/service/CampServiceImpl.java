package com.campers.camfp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.campers.camfp.entity.Camp;
import com.campers.camfp.entity.CampCalender;
import com.campers.camfp.entity.CampReview;
import com.campers.camfp.repository.CampCalenderRepository;
import com.campers.camfp.repository.CampRepository;
import com.campers.camfp.repository.CampReviewRepository;
import com.campers.camfp.util.type.TableType;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
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
	public Optional<Object> findbyId(TableType table, Long cno) {

		Optional<Object> value = null;
		switch (table) {

		case CAMP:
			value = Optional.of(campRepository.findById(cno).get());
			break;

		case CAMPREVIEW:
			value = Optional.of(campReviewRepository.findById(cno).get());
			break;

		case CAMPCALENDER:
			value = Optional.of(campCalenderRepository.findById(cno).get());
			break;

		default:

			System.out.println("Not Found Type : " + table);

			break;
		}

		if (value.isPresent()) {
			System.out.println("Value 값이 없습니다.");
			return null;
		}

		return value;
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

		Object result = null;

		switch (table) {

		case CAMP:
			Camp camp = (Camp) DTOToEntity(table, dto);
			result = campRepository.save(camp);
			break;

		case CAMPREVIEW:
			CampReview campReview = (CampReview) DTOToEntity(table, dto);
			result = campReviewRepository.save(campReview);
			break;

		case CAMPCALENDER:
			CampCalender campCalender = (CampCalender) DTOToEntity(table, dto);
			result = campCalenderRepository.save(campCalender);
			break;

		default:

			System.out.println("Not Found Type : " + table);

			break;
		}

		return (Long) result;
	}

	@Override
	public Object modify(TableType table, Long cno) {

		Object result = null;

		switch (table) {

		case CAMP:
			Camp camp = (Camp) DTOToEntity(table, cno);
			result = campRepository.save(camp);
			break;

		case CAMPREVIEW:
			CampReview campReview = (CampReview) DTOToEntity(table, cno);
			result = campReviewRepository.save(campReview);
			break;

		case CAMPCALENDER:
			CampCalender campCalender = (CampCalender) DTOToEntity(table, cno);
			result = campCalenderRepository.save(campCalender);
			break;

		default:

			System.out.println("Not Found Type : " + table);

			break;
		}

		return (Long) result;

	}

	@Override
	public List<Object> findAll(TableType table, Long cno) {

		List<Object> result = new ArrayList<>();

		switch (table) {

		case CAMP:
			campRepository.findAll().forEach(data -> {
				result.add(data);
			});
			break;

		case CAMPREVIEW:
			campReviewRepository.findAll().forEach(data -> {
				result.add(data);
			});
			break;

		case CAMPCALENDER:
			campCalenderRepository.findAll().forEach(data -> {
				result.add(data);
			});
			break;

		default:

			System.out.println("Not Found Type : " + table);

			break;
		}

		return result;

	}
}
