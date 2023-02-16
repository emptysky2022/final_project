package com.campers.camfp.repository.camp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.campers.camfp.config.type.TableType;
import com.campers.camfp.entity.camp.Camp;
import com.campers.camfp.entity.camp.CampReview;
import com.campers.camfp.entity.camp.QCamp;
import com.campers.camfp.entity.camp.QCampCalender;
import com.campers.camfp.entity.camp.QCampReview;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
<<<<<<< Updated upstream
@Repository
public class CampQuerydslImpl extends QuerydslRepositorySupport implements CampQuerydsl {
=======
//@Repository
public class CampQuerydslImpl implements CampQuerydsl {


//	@Autowired
//	private JPAQueryFactory queryFactory;
//
//	private final QCamp Q_CAMP = new QCamp("camp");
//	private final QCampReview Q_CAMP_REVIEW = new QCampReview("camp");
//	private final QCampCalender Q_CAMP_CALENDER = new QCampCalender("camp");
//
//	@Override
//	public List<?> findByAddress(TableType table, String address) {
//		
//		List<?> data = new ArrayList<>();
//		
//		switch (table) {
//		case CAMP:
//		
//			data = queryFactory.select(Q_CAMP)
//							   .from(Q_CAMP)
//							   .groupBy(Q_CAMP.address.contains(address))
//							   .fetch();
//			break;
//
//		default:
//			log.info("알수 없는 데이터 형식입니다 해당 데이터 는 table 에 없습니다. tableType : " + table);
//			break;
//
//		}
//
//		return data;
//	}
//
//	@Override
//	public List<?> findHeartRank(TableType table, int count) {
//
//		List<?> data = new ArrayList<>();
//
//		switch (table) {
//		case CAMP:
//			data = queryFactory.select(Q_CAMP)
//			                   .from(Q_CAMP)
//			                   .orderBy(Q_CAMP.heart.desc())
//			                   .limit(count).fetch();
//			break;
//
//		case CAMPREVIEW:
//			data = queryFactory.select(Q_CAMP_REVIEW)
//			                   .from(Q_CAMP_REVIEW)
//			                   .orderBy(Q_CAMP_REVIEW.heart.desc())
//			                   .limit(count).fetch();
//
//			break;
//
//		default:
//			log.info("알수 없는 데이터 형식입니다 해당 데이터 는 table 에 없습니다. tableType : " + table);
//			break;
//
//		}
//		return data;
//	}
//
//	@Override
//	public List<?> findDataOfMember(TableType table, int count) {
//		// TODO Auto-generated method stub
//		return null;
//	}
>>>>>>> Stashed changes

	private final QCamp Q_CAMP = QCamp.camp;
	private final QCampReview Q_CAMP_REVIEW = QCampReview.campReview;
	private final QCampCalender Q_CAMP_CALENDER = QCampCalender.campCalender;

	public CampQuerydslImpl() {
		super(Camp.class);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public List<?> findByAddress(TableType table, String address) {

		List<?> data = new ArrayList<>();

		switch (table) {
		case CAMP:

			JPQLQuery<Camp> camp = from(Q_CAMP);
			camp.groupBy(Q_CAMP.location.contains(address));
			camp.fetch();
			data = camp.fetch();

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
			
			JPQLQuery<Camp> camp = from(Q_CAMP);
			camp.select(Q_CAMP);
			camp.from(Q_CAMP);
			camp.orderBy(Q_CAMP.heart.desc());
			camp.limit(count);
			data = camp.fetch();
			break;

		case CAMPREVIEW:
			
			JPQLQuery<CampReview> review = from(Q_CAMP_REVIEW);
			review.select(Q_CAMP_REVIEW);
			review.from(Q_CAMP_REVIEW);
			review.orderBy(Q_CAMP_REVIEW.heart.desc());
			review.limit(count);
			data = review.fetch();

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
