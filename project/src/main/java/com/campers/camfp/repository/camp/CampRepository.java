package com.campers.camfp.repository.camp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.campers.camfp.entity.camp.Camp;

public interface CampRepository extends JpaRepository<Camp, Long> , CampQuerydsl{

	@Query("select avg(star) from CampReview c where c.camp.cno=:cno")
	Double countStar(Long cno);
}
