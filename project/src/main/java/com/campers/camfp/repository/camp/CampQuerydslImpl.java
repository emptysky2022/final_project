package com.campers.camfp.repository.camp;

import java.util.ArrayList;
import java.util.List;

import com.campers.camfp.entity.camp.Camp;
import com.campers.camfp.entity.camp.QCamp;
import com.campers.camfp.entity.camp.QCampCalender;
import com.campers.camfp.entity.camp.QCampReview;
import com.campers.camfp.util.type.TableType;
import com.fasterxml.jackson.databind.util.ArrayBuilders.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
public class CampQuerydslImpl implements CampQuerydsl {

	private final JPAQueryFactory queryFactory;

	private final static QCamp Q_CAMP = new QCamp("camp");
	private final static QCampReview Q_CAMP_REVIEW = new QCampReview("camp");
	private final static QCampCalender Q_CAMP_CALENDER = new QCampCalender("camp");

	/*
	 * @param table 검색할 테이블
	 * @param address 찾을 주소
	 * @return List<?> 형식으로 리턴
	 */
	@Override
	public List<?> findByAddress(TableType table, String address) {
		
		List<?> data = new ArrayList<>();
		
		switch (table) {
		case CAMP:
		
			data = queryFactory.select(Q_CAMP).from(Q_CAMP).groupBy(Q_CAMP.address.contains(address)).fetch();
			break;

		default:
			log.info("알수 없는 데이터 형식입니다 해당 데이터 에는 table 이 없습니다. tableType : " + table);
			break;

		}

		return null;
	}

	@Override
	/*
	 * @param table 검색할 테이블
	 * @param count 1순위 ~ count 까지 데이터
	 * @return List<?> 형식으로 리턴
	 */
	public List<?> findheartRank(TableType table, int count) {

		List<?> data = new ArrayList<>();

		switch (table) {
		case CAMP:
			data = queryFactory.select(Q_CAMP).from(Q_CAMP).orderBy(Q_CAMP.cno.desc()).limit(count).fetch();
			break;

		case CAMPREVIEW:
			data = queryFactory.select(Q_CAMP_REVIEW).from(Q_CAMP_REVIEW).orderBy(Q_CAMP_REVIEW.crno.desc())
					.limit(count).fetch();

			break;

		default:
			log.info("알수 없는 데이터 형식입니다 해당 데이터 에는 table 이 없습니다. tableType : " + table);
			break;

		}
		return data;
	}

}
