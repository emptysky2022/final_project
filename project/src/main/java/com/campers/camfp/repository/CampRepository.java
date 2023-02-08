package com.campers.camfp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.campers.camfp.entity.Camp;



public interface CampRepository extends JpaRepository<Camp, Long> {

}
