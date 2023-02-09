package com.campers.camfp.service;

import org.springframework.stereotype.Service;

import com.campers.camfp.repository.CampRepository;
import com.campers.camfp.repository.ItemRepository;

@Service
public class CampServiceImpl extends baseQueryServiceImpl {

	// private final CampRepository campRepository;
	
	public void getList() {
		SelectAllQuery(null, null);
	}
	
}
