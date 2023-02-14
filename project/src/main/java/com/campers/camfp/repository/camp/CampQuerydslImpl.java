package com.campers.camfp.repository.camp;

import java.util.ArrayList;
import java.util.List;

import com.campers.camfp.config.type.TableType;
import com.campers.camfp.entity.camp.Camp;
import com.campers.camfp.entity.camp.QCamp;
import com.campers.camfp.entity.camp.QCampCalender;
import com.campers.camfp.entity.camp.QCampReview;
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

	@Override
	public List<?> findByAddress(TableType table, String address) {
		
		List<?> data = new ArrayList<>();
		
		switch (table) {
		case CAMP:
		
			data = queryFactory.select(Q_CAMP)
							   .from(Q_CAMP)
							   .groupBy(Q_CAMP.address.contains(address))
							   .fetch();
			break;

		default:
			log.info("알수 없는 데이터 형식입니다 해당 데이터 는 table 에 없습니다. tableType : " + table);
			break;

		}

		return data;
	}

	@Override
	public List<?> findHeartRank(TableType table, int count) {

		List<?> data = new ArrayList<>();

		switch (table) {
		case CAMP:
			data = queryFactory.select(Q_CAMP)
			                   .from(Q_CAMP)
			                   .orderBy(Q_CAMP.heart.desc())
			                   .limit(count).fetch();
			break;

		case CAMPREVIEW:
			data = queryFactory.select(Q_CAMP_REVIEW)
			                   .from(Q_CAMP_REVIEW)
			                   .orderBy(Q_CAMP_REVIEW.heart.desc())
			                   .limit(count).fetch();

			break;

		default:
			log.info("알수 없는 데이터 형식입니다 해당 데이터 는 table 에 없습니다. tableType : " + table);
			break;

		}
		return data;
	}

	@Override
	public List<?> findDataOfMember(TableType table, int count) {
		// TODO Auto-generated method stub
		return null;
	}
}
