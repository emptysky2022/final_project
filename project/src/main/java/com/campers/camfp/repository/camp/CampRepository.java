package com.campers.camfp.repository.camp;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campers.camfp.entity.camp.Camp;

public interface CampRepository extends JpaRepository<Camp, Long> , CampQuerydsl{

}
