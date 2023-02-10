package com.campers.camfp.service;

import com.campers.camfp.dto.BoardDTO;
import com.campers.camfp.entity.Board;

public interface BoardService {
	
	Long register(BoardDTO boardDTO); 
	
	
	// dto -> entity
	default Board dtoToEntity(BoardDTO boardDTO) {
		Board entityBoard = Board.builder().bno(boardDTO.getBno())
									 .title(boardDTO.getTitle())
									 .content(boardDTO.getContent())
									 .member(boardDTO.getMember())
									 .category(boardDTO.getCategory())
									 .count(boardDTO.getCount())
									 .heart(boardDTO.getHeart())
									 .build();
		
		return entityBoard;
	}
	
	default BoardDTO entityToDTO(Board board) {
		
		BoardDTO boardDTO = BoardDTO.builder().bno(board.getBno())
										      .title(board.getTitle())
										      .content(board.getContent())
										      .member(board.getMember())
										      .category(board.getCategory())
										      .count(board.getHeart())
										      .build(); // regDate, updateDate를 넣으면 빨간줄 쳐짐
		return boardDTO;
	}
}
