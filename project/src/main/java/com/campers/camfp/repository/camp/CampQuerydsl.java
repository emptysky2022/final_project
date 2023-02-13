package com.campers.camfp.repository.camp;

import java.util.List;

import com.campers.camfp.util.type.TableType;

public interface CampQuerydsl {

	/*
	 * @param table 검색할 테이블
	 * @param address 찾을 주소
	 * @return List<?> 형식으로 리턴
	 */
	public List<?> findByAddress(TableType table, String data);
	
	public List<?> findheartRank(TableType table, int count);
	
	
}
	