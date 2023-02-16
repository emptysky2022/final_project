package com.campers.camfp.repository.item;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.campers.camfp.entity.item.Item;
import com.campers.camfp.entity.item.QItem;
import com.querydsl.core.BooleanBuilder;
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
	public Page<Item> findBySearch(String category, String keyword, Pageable pageable) {
		QItem item = QItem.item;
		
		JPQLQuery<Item> jpqlquery = from(item);
		jpqlquery.select(item);
		
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		BooleanExpression expression = item.ino.gt(0);
		
		booleanBuilder.and(expression);
		
		if (!StringUtils.isNullOrEmpty(category)) {			
			BooleanBuilder categoryBuilder = new BooleanBuilder();
			categoryBuilder.or(item.category1.eq(category));
			categoryBuilder.or(item.category2.eq(category));
			
			booleanBuilder.and(categoryBuilder);
		}
		
		if(!StringUtils.isNullOrEmpty(keyword)) {
			BooleanExpression keywordExpression = item.name.contains(keyword);
			booleanBuilder.and(keywordExpression);
		}
		
		Sort sort = pageable.getSort();
		List<OrderSpecifier> orders = new ArrayList<>();
		
		sort.stream().forEach(order -> {
			Order direction = order.isAscending()? Order.ASC : Order.DESC;
			String prop = order.getProperty();
			log.info(prop + "====================asdfasdf====================");
			
			PathBuilder orderByExpression = new PathBuilder<>(Item.class, "item");
			orders.add(new OrderSpecifier(direction, orderByExpression.get(prop)));
		});
		
		jpqlquery.orderBy(orders.stream().toArray(OrderSpecifier[]::new));
		jpqlquery.where(booleanBuilder);
		jpqlquery.offset(pageable.getOffset());
		jpqlquery.limit(pageable.getPageSize());
		
		List<Item> result = jpqlquery.fetch();
		
		log.info(result);
		
		long resultCount = jpqlquery.fetchCount();
		log.info("result count : " + resultCount);
		
		return new PageImpl<Item>(result, pageable, resultCount);
	}

}
