package com.campers.camfp.service.board;


import com.campers.camfp.dto.board.BoardDTO;
import com.campers.camfp.dto.page.PageRequestDTO;
import com.campers.camfp.dto.page.PageResultDTO;
import com.campers.camfp.entity.board.Board;
import com.campers.camfp.entity.member.Member;

public interface BoardService {
	
	
	// 게시판 리스트 호출
	PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);
	
	// 게시글 등록
	Long register(BoardDTO boardDTO); 

	// 게시글 상세 조회
	BoardDTO read(Long bno);
	
	// 게시글 수정
	void modify(BoardDTO boardDTO);
	
	// 게시글 지우기(댓글 선삭제 후 게시글 삭제됨)
	void removeWithReplies(BoardDTO boardDTO);
	
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
	
	default BoardDTO entityToDTO(Board board, Member member, Long replyCount, Long heartCount) {
		BoardDTO boardDTO = BoardDTO.builder().bno(board.getBno())
										      .title(board.getTitle())
											  .content(board.getContent())
											  .heart(heartCount.intValue())
										      .mno(member.getMno())
										      .nickname(member.getNickname())
										      .category(board.getCategory())
										      .replyCount(replyCount.intValue())
										      .count(board.getCount())
										      .regDate(board.getRegDate())
										      .updateDate(board.getUpdateDate())
										      .build();
		return boardDTO;
	}
}