package com.campers.camfp.service.camp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.campers.camfp.entity.camp.Camp;
import com.campers.camfp.entity.camp.CampCalender;
import com.campers.camfp.entity.camp.CampReview;
import com.campers.camfp.repository.camp.CampCalenderRepository;
import com.campers.camfp.repository.camp.CampRepository;
import com.campers.camfp.repository.camp.CampReviewRepository;
import com.campers.camfp.util.type.TableType;

import lombok.RequiredArgsConstructor;
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
	public Object findbyId(TableType table, Long num) {

		Object value = null;
		switch (table) {

		case CAMP:
			Camp camp = campRepository.findById(num).get();
			value = camp;
			break;

		case CAMPREVIEW:
			CampReview campreview = campReviewRepository.findById(num).get();
			value = campreview;
			break;

		case CAMPCALENDER:
			CampCalender campCelender= campCalenderRepository.findById(num).get();
			value = campCelender;
			break;

		default:

			System.out.println("Not Found Type : " + table);

			break;
		}

		return value;
	}

	@Override
	public void remove(TableType table, Long num) {

		switch (table) {

		case CAMP:
			campRepository.deleteById(num);
			break;

		case CAMPREVIEW:
			campReviewRepository.deleteById(num);
			break;

		case CAMPCALENDER:
			campCalenderRepository.deleteById(num);
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
	public Object modify(TableType table, Object dto) {

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