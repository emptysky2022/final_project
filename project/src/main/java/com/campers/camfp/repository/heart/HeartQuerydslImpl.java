package com.campers.camfp.repository.heart;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.campers.camfp.config.type.ProductType;
import com.campers.camfp.dto.heart.HeartDTO;
import com.campers.camfp.entity.camp.QCamp;
import com.campers.camfp.entity.heart.Heart;
import com.campers.camfp.entity.heart.QHeart;
import com.campers.camfp.repository.camp.CampQuerydsl;
import com.fasterxml.jackson.databind.util.ArrayBuilders.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class HeartQuerydslImpl extends QuerydslRepositorySupport implements HeartQuerydsl {

	private final QHeart Q_Heart = QHeart.heart;

	public HeartQuerydslImpl() {
		super(Heart.class);
	}

	public Heart findHeart(HeartDTO dto) {

		Heart data = null;

		JPQLQuery<Heart> heart = from(Q_Heart);

		heart.where(Q_Heart.member.mno.eq(dto.getMno())
				.and(Q_Heart.productType.eq(dto.getProductType()))
				.and(Q_Heart.productNum.eq(dto.getProductNum())))
				.fetch();
		data =  heart.fetchOne();
		log.info("실제데이터");
		log.info(data);
		return data;

	}

}
