package com.emptysky.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emptysky.project.entity.Camp;

public interface CampRepository extends JpaRepository<Camp, Long> {

}
