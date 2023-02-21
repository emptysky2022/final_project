package com.campers.camfp.repository.item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.campers.camfp.entity.item.ItemReview;

public interface ItemReviewRepository extends JpaRepository<ItemReview, Long>{

	@Query("select r from ItemReview r where r.item.ino=:ino order by irno desc")
	List<ItemReview> getReviewWithIno(Long ino);
	
	@Query("select avg(star) from ItemReview r where r.item.ino=:ino")
	double countStar(Long ino);
}
