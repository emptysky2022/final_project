package com.campers.camfp.service.board;

import com.campers.camfp.dto.board.BoardDTO;
import com.campers.camfp.dto.page.PageRequestDTO;
import com.campers.camfp.dto.page.PageResultDTO;
import com.campers.camfp.entity.board.Board;
import com.campers.camfp.entity.member.Member;

public interface BoardService {
	
	// 게시글 상세 조회
	BoardDTO read(Long bno);
	
	Long register(BoardDTO boardDTO); 
	
	// SH
	PageResultDTO<BoardDTO, Board> getList(PageRequestDTO pageRequestDTO);
	
	
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
										      .regDate(board.getRegDate())
										      .updateDate(board.getUpdateDate())
										      .build();
		return boardDTO;
	}
}
