package com.campers.camfp.service;

import org.springframework.data.jpa.repository.JpaRepository;
import com.campers.camfp.util.SelectTable;

public interface baseQueryService {

	void deleteQuery(SelectTable table , JpaRepository<?, ?> repository);
	
	void deleteAllQuery(SelectTable table , JpaRepository<?, ?> repository);
	
	void SelectQuery(SelectTable table , JpaRepository<?, ?> repository);
	
	void SelectAllQuery(SelectTable table , JpaRepository<?, ?> repository);
	
	
}
