package com.campers.camfp.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campers.camfp.entity.board.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
