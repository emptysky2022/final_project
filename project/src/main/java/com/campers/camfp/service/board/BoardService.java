package com.campers.camfp.service.board;

import com.campers.camfp.dto.board.BoardDTO;
import com.campers.camfp.entity.board.Board;
import com.campers.camfp.entity.member.Member;

public interface BoardService {
	
	Long register(BoardDTO boardDTO); 
	
	
	// dto -> entity
	default Board dtoToEntity(BoardDTO boardDTO) {
		Board entityBoard = Board.builder().bno(boardDTO.getBno())
									 .title(boardDTO.getTitle())
									 .content(boardDTO.getContent())
									 .member(Member.builder().mno(boardDTO.getMno()).build())
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
										      .mno(board.getMember().getMno())
										      .nickname(board.getMember().getNickname())
										      .category(board.getCategory())
										      .count(board.getHeart())
										      .build(); // regDate, updateDate를 넣으면 빨간줄 쳐짐
		return boardDTO;
	}
}
