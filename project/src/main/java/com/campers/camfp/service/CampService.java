package com.campers.camfp.service;

import java.util.Optional;

import com.campers.camfp.util.type.TableType;

public interface CampService {

	public Optional<Object> findbyId(TableType table, Long cno);

	public void remove(TableType table, Long cno);

	public Object modify(TableType table, Long cno);
	
	public Long register(TableType table, Object dto);



}
