package com.campers.camfp.repository.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.campers.camfp.entity.item.Item;
import com.querydsl.core.BooleanBuilder;

public interface ItemQuerydsl {
	
	/**
	 * 조건부 검색
	 * 
	 */
	Page<Item> findBySearch(String category, String keyword, Pageable pageable);
	
}
