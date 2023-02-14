package com.campers.camfp.repository.camp;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.campers.camfp.entity.camp.Camp;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class QueryRepositorySupport extends QuerydslRepositorySupport{

	private final JPAQueryFactory queryFactory;
	
	public QueryRepositorySupport(JPAQueryFactory queryFactory) {
		super(Camp.class);
		this.queryFactory = queryFactory;
	}
	
	public List<?> findByName(){
		
		
		
		return null;
	}
	
	

}
