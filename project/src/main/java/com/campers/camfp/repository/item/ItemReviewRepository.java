package com.campers.camfp.repository.item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.campers.camfp.entity.item.ItemReview;

public interface ItemReviewRepository extends JpaRepository<ItemReview, Long>{

	@Query("select r from ItemReview r where r.item.ino=:ino")
	List<ItemReview> getReviewWithIno(Long ino);
	
}
