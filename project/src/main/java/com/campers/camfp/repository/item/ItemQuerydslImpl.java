package com.campers.camfp.repository.item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.campers.camfp.entity.item.Item;
import com.campers.camfp.entity.item.ItemReview;
import com.campers.camfp.entity.item.QItem;
import com.campers.camfp.entity.item.QItemReview;
import com.campers.camfp.entity.member.QMember;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class ItemQuerydslImpl extends QuerydslRepositorySupport implements ItemQuerydsl{
	public ItemQuerydslImpl() {
		super(Item.class);
	}	
	
	@Override
	public Page<Object[]> findBySearch(String category, String keyword, Pageable pageable) {
		QItem item = QItem.item;
		QMember member = QMember.member;
		QItemReview review = QItemReview.itemReview;
		
		JPQLQuery<Item> jpqlquery = from(item);
		jpqlquery.leftJoin(member).on(item.member.eq(member));
		jpqlquery.leftJoin(review).on(review.item.eq(item));
		
		JPQLQuery<Tuple> tuple = jpqlquery.select(item, member, review.count());
		
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		BooleanExpression expression = item.ino.gt(0);
		
		booleanBuilder.and(expression);
		
		log.info("asdf : " + category + keyword);
		if (!StringUtils.isNullOrEmpty(category)) {			
			BooleanBuilder categoryBuilder = new BooleanBuilder();
			categoryBuilder.or(item.category1.eq(category));
			categoryBuilder.or(item.category2.eq(category));
			
			booleanBuilder.and(categoryBuilder);
		}
		
		if (!StringUtils.isNullOrEmpty(keyword)) {			
			BooleanExpression keywordExpression = item.name.contains(keyword);
			booleanBuilder.and(keywordExpression);
		}
		
		Sort sort = pageable.getSort();
		List<OrderSpecifier> orders = new ArrayList<>();
		
		sort.stream().forEach(order -> {
			Order direction = order.isAscending()? Order.ASC : Order.DESC;
			String prop = order.getProperty();
			log.info(prop + " property");
			
			PathBuilder orderByExpression = new PathBuilder<>(Item.class, "item");
			orders.add(new OrderSpecifier(direction, orderByExpression.get(prop)));
		});
		
		tuple.orderBy(orders.stream().toArray(OrderSpecifier[]::new));
		tuple.where(booleanBuilder);
		tuple.groupBy(item.ino);
		log.info("pageable size : " + pageable.getPageSize() + ", pageable offset : " + pageable.getOffset());
		tuple.offset(pageable.getOffset());
		tuple.limit(pageable.getPageSize());
		
		List<Tuple> result = tuple.fetch();
		
		log.info(result.size());
		
		long resultCount = tuple.fetchCount();
		log.info("result count : " + resultCount);
		
		return new PageImpl<Object[]>(result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable, resultCount);
	}

}
