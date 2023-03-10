package com.campers.camfp.repository.camp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.print.DocFlavor.READER;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.querydsl.sql.types.Null;

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
			log.info("?????? ?????? ????????? ??????????????? ?????? ????????? ??? table ??? ????????????. tableType : " + table);
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
			if (findType == "?????????") {
				camp.orderBy(Q_CAMP.count.desc());
			}
			if (findType == "?????????") {
				camp.orderBy(Q_CAMP.star.desc());
			}
			if (findType == "?????????") {
				camp.orderBy(Q_CAMP.heart.desc());
			}
			camp.limit(count);
			data = camp.fetch();
			break;

		case CAMPREVIEW:

			JPQLQuery<CampReview> review = from(Q_CAMP_REVIEW);
			review.select(Q_CAMP_REVIEW);
			review.from(Q_CAMP_REVIEW);

			if (findType == "?????????") {
				review.orderBy(Q_CAMP.heart.desc());
			}
			if (findType == "?????????") {
				review.orderBy(Q_CAMP.count.desc());
			}

			review.limit(count);
			data = review.fetch();

			break;

		default:
			log.info("?????? ?????? ????????? ??????????????? ?????? ????????? ??? table ??? ????????????. tableType : " + table);
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

			// ???????????? , ???????????? ?????????
			for (String query : findDatas) {
				switch (query) {
				case "??????":
				case "?????????":
				case "?????????":
				case "?????????":
					conditionCampBuilder.or(QCamp.camp.camptype.contains(query));
					break;
				case "?????????":
					direction = new OrderSpecifier(Order.DESC, Q_CAMP.heart);
					break;
				case "?????????":
					direction = new OrderSpecifier(Order.DESC, Q_CAMP.count);
					break;
				}
			}

			data = camp.where(conditionCampBuilder).orderBy(direction).limit(6).fetch();

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
			log.info("?????? ?????? ????????? ??????????????? ?????? ????????? ??? table ??? ????????????. tableType : " + table);
			break;

		}
		return data;
	}

	@Override
	@Transactional
	public void addData(TableType table, Long no, String addData, int num) {

		JPAUpdateClause update;

		// ???????????? ?????? ??? ????????? ?????? ????????? case ??? ???
		switch (table) {

		case CAMP:

			update = new JPAUpdateClause(getEntityManager(), Q_CAMP);

			switch (addData) {

			case "count":
				update.set(Q_CAMP.count, findData(table, no, addData) + num).where(Q_CAMP.cno.eq(no)).execute();
				break;

			case "heart":
				update.set(Q_CAMP.heart, findData(table, no, addData) + num).where(Q_CAMP.cno.eq(no)).execute();
				break;

			case "star":
				update.set(Q_CAMP.star, findData(table, no, addData) + num).where(Q_CAMP.cno.eq(no)).execute();
				break;

			}

			break;

		case CAMPREVIEW:
			update = new JPAUpdateClause(getEntityManager(), Q_CAMP_REVIEW);

			switch (addData) {

			case "count":
				log.info("??? ????????? ??????");
				break;

			case "heart":
				update.set(Q_CAMP_REVIEW.heart, findData(table, no, addData) + 1)
						.where(Q_CAMP_REVIEW.crno.eq((long) no.intValue())).execute();
				break;

			case "star":
				log.info("??? ????????? ??????");
				break;
			}
			break;
		}
	}

	@Override
	public int findData(TableType table, Long no, String findData) {

		int data = 0;

		switch (table) {

		case CAMP:
			JPQLQuery<Camp> camp = from(Q_CAMP);

			switch (findData) {

			case "count":
				camp.select(Q_CAMP.count);
				camp.where(Q_CAMP.cno.eq(no));

				// ??? fetchone ?????? ????????? ?????? ???????????? ????????? ??????.
				Object buffer = camp.fetch().get(0);

				Integer value = (Integer) buffer;
				data = value;
				//
				break;

			case "heart":
				camp.select(Q_CAMP.heart);
				camp.where(Q_CAMP.cno.eq(no));

				// ??? fetch one ?????? ????????? ?????? ???????????? ????????? ??????.
				Object buffer2 = camp.fetch().get(0);
				Integer value2 = (Integer) buffer2;
				data = value2;
				break;

			case "star":
				camp.select(Q_CAMP.star);
				camp.where(Q_CAMP.cno.eq(no));

				// ??? fetch one ?????? ????????? ?????? ???????????? ????????? ??????.
				Object buffer3 = camp.fetch().get(0);
				Integer value3 = (Integer) buffer3;
				data = value3;
				break;
			}

			break;

		case CAMPREVIEW:
			JPQLQuery<CampReview> review = from(Q_CAMP_REVIEW);

			switch (findData) {

			case "count":
				log.info("???????????? ??????");
				break;

			case "heart":
				review.select(Q_CAMP.heart);
				review.where(Q_CAMP_REVIEW.crno.eq((long) no.intValue()));

				// ??? fetchone ?????? ????????? ?????? ???????????? ????????? ??????.
				Object buffer = review.fetch().get(0);
				Integer value = (Integer) buffer;
				data = value;
				break;

			case "star":
				log.info("???????????? ??????");
				break;
			}

			break;

		}

		return data;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Page<Object[]> findManayDataOfCamp(String[] findDatas, String[] findLocations, Pageable pageable) {

		List<Camp> data = new ArrayList<>();
		BooleanBuilder conditionBuilder = new BooleanBuilder();
		BooleanBuilder conditionLocaitonBuilder = new BooleanBuilder();
		OrderSpecifier<?> direction = null;

		JPQLQuery<Tuple> tuple = from(Q_CAMP).select(Q_CAMP.cno, Q_CAMP.member.mno, Q_CAMP.name, Q_CAMP.thumbnail,
				Q_CAMP.location, Q_CAMP.camptype, Q_CAMP.campintroduce, Q_CAMP.address, Q_CAMP.unit, Q_CAMP.count,
				Q_CAMP.star, Q_CAMP.heart);

		JPQLQuery<Camp> camp = from(Q_CAMP);
		camp.select(Q_CAMP);
		camp.from(Q_CAMP);

		// ?????? ????????? ????????? ????????? ?????? ????????? default ???
		log.info(findLocations[0]);
		if (findLocations[0] == "noData") {
			conditionLocaitonBuilder.or(QCamp.camp.location.contains("?????????"));
			conditionLocaitonBuilder.or(QCamp.camp.location.contains("?????????"));
			conditionLocaitonBuilder.or(QCamp.camp.location.contains("?????????"));
			conditionLocaitonBuilder.or(QCamp.camp.location.contains("?????????"));
			conditionLocaitonBuilder.or(QCamp.camp.location.contains("?????????"));
			conditionLocaitonBuilder.or(QCamp.camp.location.contains("?????????"));
			conditionLocaitonBuilder.or(QCamp.camp.location.contains("?????????"));
			conditionLocaitonBuilder.or(QCamp.camp.location.contains("?????????"));
			conditionLocaitonBuilder.or(QCamp.camp.location.contains("?????????"));
			conditionLocaitonBuilder.or(QCamp.camp.location.contains("?????????"));
			conditionLocaitonBuilder.or(QCamp.camp.location.contains("????????????"));
			conditionLocaitonBuilder.or(QCamp.camp.location.contains("????????????"));
			conditionLocaitonBuilder.or(QCamp.camp.location.contains("????????????"));
			conditionLocaitonBuilder.or(QCamp.camp.location.contains("????????????"));
			conditionLocaitonBuilder.or(QCamp.camp.location.contains("????????????"));
			conditionLocaitonBuilder.or(QCamp.camp.location.contains("????????????"));
			conditionLocaitonBuilder.or(QCamp.camp.location.contains("?????????"));
		} else {

			// ????????? ?????? ??????
			for (String locationQuery : findLocations) {
				conditionLocaitonBuilder.or(QCamp.camp.location.contains(locationQuery));
			}
		}

		// ???????????? , ???????????? ?????????
		for (String query : findDatas) {
			switch (query) {
			case "??????":
			case "?????????":
			case "?????????":
			case "?????????":
				conditionBuilder.or(QCamp.camp.camptype.contains(query));
				break;
			case "?????????":
				direction = new OrderSpecifier(Order.DESC, Q_CAMP.heart);
				break;
			case "?????????":
				direction = new OrderSpecifier(Order.DESC, Q_CAMP.count);
				break;
			}
		}

		// ?????? ????????? ????????? ????????? ?????? ????????? default ???
		if (direction == null) {
			direction = new OrderSpecifier(Order.DESC, Q_CAMP.heart);

		}

		log.info(conditionBuilder);
		log.info(conditionBuilder.not());

		if (conditionBuilder.not() == null) {
			log.info("null ???");
			conditionBuilder.or(QCamp.camp.camptype.contains("??????"));
			conditionBuilder.or(QCamp.camp.camptype.contains("?????????"));
			conditionBuilder.or(QCamp.camp.camptype.contains("?????????"));
			conditionBuilder.or(QCamp.camp.camptype.contains("?????????"));
		}

		tuple.where(conditionBuilder.and(conditionLocaitonBuilder)).orderBy(direction).offset(pageable.getOffset())
				.limit(pageable.getPageSize()).fetch();

		List<Tuple> result = tuple.fetch(); // ?????? ?????? ?????????
		log.info("result : " + result);

		long count = tuple.fetchCount();
		log.info("count : " + count);

		return new PageImpl<Object[]>(tuple.stream().map(Tuple::toArray).collect(Collectors.toList()), pageable, count);
	}

	@Override
	public List<Object> findReservationDataofMember(String NickName) {
		List<Object> value = new ArrayList<>();
		List<CampCalender> buffers = new ArrayList<>();

		// ????????? ?????? ?????? ?????? ????????????
		buffers = from (Q_CAMP_CALENDER).where(Q_CAMP_CALENDER.reservationer.eq(NickName)).fetch();
		log.info(buffers.size());
		
		for (var buffer : buffers) {
			Long cno = buffer.getCamp().getCno();
			Object data = from(Q_CAMP).select(Q_CAMP).where(Q_CAMP.cno.eq(cno)).fetchFirst();
			
			value.add(new Object[] {buffer,data});
		}

		return value;
	}
}
