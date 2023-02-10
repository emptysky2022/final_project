package com.campers.camfp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campers.camfp.entity.CampCalender;

public interface CampCalenderRepository extends JpaRepository<CampCalender, Long> {
	
}
