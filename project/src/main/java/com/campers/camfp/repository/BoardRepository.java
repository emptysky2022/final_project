package com.campers.camfp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campers.camfp.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {


	
}
