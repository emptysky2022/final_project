package com.campers.camfp.repository.camp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.print.DocFlavor.READER;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.campers.camfp.config.type.TableType;
import com.campers.camfp.entity.camp.Camp;
import com.campers.camfp.entity.camp.CampCalender;
import com.campers.camfp.entity.camp.CampReview;
import com.campers.camfp.entity.camp.QCamp;
import com.campers.camfp.entity.camp.QCampCalender;
import com.campers.camfp.entity.camp.QCampReview;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class CampQuerydslImpl extends QuerydslRepositorySupport implements CampQuerydsl {

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
	public List<?> findHeartOrCountRank(TableType table, int count, String findType) {

		List<?> data = new ArrayList<>();

		switch (table) {
		case CAMP:
			
			JPQLQuery<Camp> camp = from(Q_CAMP);
			camp.select(Q_CAMP);
			camp.from(Q_CAMP);
			if (findType == "조회순") {
				camp.orderBy(Q_CAMP.heart.desc());				
			}
			if (findType == "별점순") {
				camp.orderBy(Q_CAMP.count.desc());								
			}
			camp.limit(count);
			data = camp.fetch();
			break;

		case CAMPREVIEW:
			
			JPQLQuery<CampReview> review = from(Q_CAMP_REVIEW);
			review.select(Q_CAMP_REVIEW);
			review.from(Q_CAMP_REVIEW);
			
			if (findType == "별점순") {
				review.orderBy(Q_CAMP.heart.desc());				
			}
			if (findType == "조회순") {
				review.orderBy(Q_CAMP.count.desc());								
			}
			
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
	public List<?> findDataOfMember(TableType table, Long count) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	@Transactional
	public List<?> findDataOfCamp(TableType table, Long cno, String findData) {
		
		List<?> data = new ArrayList<>();
		BooleanBuilder conditionBuilder = new BooleanBuilder();
		switch (table) {
		
		case CAMP:
			JPQLQuery<Camp> camp = from(Q_CAMP);
			
			String[] find = findData.split(",");
			
			
			camp.select(Q_CAMP);
			camp.from(Q_CAMP);
			
			for (String query : find) {
				switch(query) {
				case "일반":
				case "카라반":
				case "글램핑":
				case "자동차":
					conditionBuilder.or(QCamp.camp.camptype.contains(query));
					break;
				case "별점순":
					camp.orderBy(Q_CAMP.heart.desc());
						break;
				case "조회순":
					camp.orderBy(Q_CAMP.count.desc());
						break;	
				}
			}
			
			data = camp.where(conditionBuilder)
                    .limit(6)
                    .fetch();

			break;
			
		
		case CAMPREVIEW:
			JPQLQuery<CampReview> review = from(Q_CAMP_REVIEW);
			review.where(Q_CAMP_REVIEW.camp.cno.eq(cno));
			data = review.fetch();
			break;
			
		case CAMPCALENDER:
			JPQLQuery<CampCalender> calender = from(Q_CAMP_CALENDER);	
			calender.where(Q_CAMP_CALENDER.camp.cno.eq(cno));
			data = calender.fetch();
			break;

		default:
			log.info("알수 없는 데이터 형식입니다 해당 데이터 는 table 에 없습니다. tableType : " + table);
			break;

		}
		return data;
	}


	@Override
	public void addData(TableType table, Long no, String addData) {
		List<?> data = new ArrayList<>();
		
		BooleanBuilder conditionBuilder = new BooleanBuilder();
		JPAUpdateClause update;  

		// 두개지만 추가 될 가능성 없지 않아서 case 문 씀
		switch(table) {
		
		case CAMP :
			
			update = new JPAUpdateClause(getEntityManager(), Q_CAMP);
			
			switch(addData) {
			 
			case "count":
				 update.set(Q_CAMP.count, 1)
				.where(Q_CAMP.cno.eq(no))
				.execute();
				break;
				
			case "heart": 
				 update.set(Q_CAMP.heart, 1)
				.where(Q_CAMP.cno.eq(no))
				.execute();
				break;
				
			case "star": 
				 update.set(Q_CAMP.star, 1)
				.where(Q_CAMP.cno.eq(no))
				.execute();
				break;
				
			}
			
			
			break;
			
		case CAMPREVIEW :
			update = new JPAUpdateClause(getEntityManager(), Q_CAMP_REVIEW);
			
			switch(addData) {
			 
			case "count":
				log.info("난 그런거 없음");
				break;
				
			case "heart": 
				 update.set(Q_CAMP_REVIEW.heart, 1)
				.where(Q_CAMP_REVIEW.crno.eq(no.intValue()))
				.execute();
				break;
				
			case "star": 
				log.info("난 그런거 없음");
				break;
				
			}
			
			break;
		}
	}
}
