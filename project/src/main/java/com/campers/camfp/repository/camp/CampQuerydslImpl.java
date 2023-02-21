package com.campers.camfp.repository.camp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.print.DocFlavor.READER;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
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
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
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


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<?> findDataOfCamp(TableType table, Long cno, String[] findDatas) {
		
		List<?> data = new ArrayList<>();
		BooleanBuilder conditionCampBuilder = new BooleanBuilder();
		OrderSpecifier<?> direction = null;
		switch (table) {
		
		case CAMP:
			JPQLQuery<Camp> camp = from(Q_CAMP);
			
			camp.select(Q_CAMP);
			camp.from(Q_CAMP);
			
			// 위치 종류 없음 폐기 
			
			
			
			// 캠프종류 , 별점으로 찾는곳
			for (String query : findDatas) {
				switch(query) {
				case "일반":
				case "카라반":
				case "글램핑":
				case "자동차":
					conditionCampBuilder.or(QCamp.camp.camptype.contains(query));
					break;
				case "별점순":
					direction = new OrderSpecifier(Order.DESC, Q_CAMP.heart);
						break;
				case "조회순":
					direction = new OrderSpecifier(Order.DESC, Q_CAMP.count);
						break;	
				}
			}
			
			data = camp.where(conditionCampBuilder)
					.orderBy(direction)
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
	@Transactional
	public void addData(TableType table, Long no, String addData) {
		
		JPAUpdateClause update;  

		// 두개지만 추가 될 가능성 없지 않아서 case 문 씀
		switch(table) {
		
		case CAMP :
			
			update = new JPAUpdateClause(getEntityManager(), Q_CAMP);
			
			switch(addData) {
			 
			case "count":
				 update.set(Q_CAMP.count, findData(table, no, addData) + 1)
				.where(Q_CAMP.cno.eq(no))
				.execute();
				break;
				
			case "heart": 
				 update.set(Q_CAMP.heart, findData(table, no, addData) + 1)
				.where(Q_CAMP.cno.eq(no))
				.execute();
				break;
				
			case "star": 
				 update.set(Q_CAMP.star, findData(table, no, addData) + 1)
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
				 update.set(Q_CAMP_REVIEW.heart, findData(table, no, addData) + 1)
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


	@Override
	public int findData(TableType table, Long no, String findData) {
		
		int data =0;
		
		switch(table) {
		
		case CAMP :
				JPQLQuery<Camp> camp = from(Q_CAMP);
				
				
			switch(findData) {
			
			case "count":
				camp.select(Q_CAMP.count);
				camp.where(Q_CAMP.cno.eq(no));
				
				// 왜 fetchone 으로 못쓰는 걸까 알아보고 바꿔야 겠다.
				Object buffer = camp.fetch().get(0);
				Integer value = (Integer) buffer;
				data = value;
				//
				break;
			
			case "heart": 
				camp.select(Q_CAMP.heart);
				camp.where(Q_CAMP.cno.eq(no));

				// 왜 fetchone 으로 못쓰는 걸까 알아보고 바꿔야 겠다.
				Object buffer2 = camp.fetch().get(0);
				Integer value2 = (Integer) buffer2;
				data = value2;			
			 	break;
			
			case "star": 
				camp.select(Q_CAMP.star);
				camp.where(Q_CAMP.cno.eq(no));
				
				// 왜 fetchone 으로 못쓰는 걸까 알아보고 바꿔야 겠다.
				Object buffer3 = camp.fetch().get(0);
				Integer value3 = (Integer) buffer3;
				data = value3;
				break;
			}
			

			break;

			case CAMPREVIEW :
				JPQLQuery<CampReview> review = from(Q_CAMP_REVIEW);
				
				switch(findData) {
				
				case "count":
					log.info("난그런거 없음");
					break;
				
				case "heart": 
					review.select(Q_CAMP.heart);
					review.where(Q_CAMP_REVIEW.crno.eq(no.intValue()));
					
					// 왜 fetchone 으로 못쓰는 걸까 알아보고 바꿔야 겠다.
					Object buffer = review.fetch().get(0);
					Integer value = (Integer) buffer;
					data = value;
				 	break;
				
				case "star": 
					log.info("난그런거 없음");
					break;
				}
				
			
			break;
		
		}
		
		
		return data;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Camp> findManayDataOfCamp(String[] findDatas, String[] findLocations) {
		
		List<Camp> data = new ArrayList<>();
		BooleanBuilder conditionBuilder = new BooleanBuilder();
		BooleanBuilder conditionLocaitonBuilder = new BooleanBuilder();
		OrderSpecifier<?> direction = null;
	
			JPQLQuery<Camp> camp = from(Q_CAMP);
			camp.select(Q_CAMP);
			camp.from(Q_CAMP);


			// 캠핑장 위치 종류
			for (String locationQuery : findLocations) {
				conditionLocaitonBuilder.or(QCamp.camp.location.contains(locationQuery));
			}
			
			
			
			// 캠프종류 , 별점으로 찾는곳
			for (String query : findDatas) {
				switch(query) {
				case "일반":
				case "카라반":
				case "글램핑":
				case "자동차":
					conditionBuilder.or(QCamp.camp.camptype.contains(query));
					break;
				case "별점순":
					direction = new OrderSpecifier(Order.DESC, Q_CAMP.heart);
						break;
				case "조회순":
					direction = new OrderSpecifier(Order.DESC, Q_CAMP.count);
						break;	
				}
			}
			
			data = camp.where(conditionBuilder.and(conditionLocaitonBuilder))
					.orderBy(direction)
                    .limit(6)
                    .fetch();

		
		return data;
	}
}
