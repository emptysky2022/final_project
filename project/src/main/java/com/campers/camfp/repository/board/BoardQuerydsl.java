package com.campers.camfp.repository.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.campers.camfp.entity.board.Board;

public interface BoardQuerydsl {

	Board searchBoard();
	
	Page<Object[]> searchPage(String type, String keyword, String category, Pageable pageable);
	
}