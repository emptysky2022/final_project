package com.campers.camfp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.campers.camfp.entity.CampReview;


public interface CampReviewRepository extends JpaRepository<CampReview, Long> {

}
