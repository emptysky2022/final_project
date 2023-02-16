package com.campers.camfp.service.board;

import java.util.List;

import com.campers.camfp.dto.board.ReplyDTO;
import com.campers.camfp.entity.board.Board;
import com.campers.camfp.entity.board.Reply;

public interface ReplyService {
	
	// 게시판 List로 가져오기
	List<ReplyDTO> getListOfBoard(Long bno);
	
	// 댓글 등록
	Long replyRegister(ReplyDTO replyDTO); 
	
	// 댓글 수정
	void modify(ReplyDTO replyDTO);
	
	// 댓글 삭제
	void remove(Long rno);
	
	// dto -> entity
	default Reply dtoToEntity(ReplyDTO replyDTO) {
		
		Reply reply = Reply.builder().board(Board.builder().bno(replyDTO.getBno()).build())
									 .replyer(replyDTO.getReplyer())
									 .content(replyDTO.getContent())
									 .heart(replyDTO.getHeart())
									 .build();
		
		return reply;
	}
	
	// entity -> dto
	default ReplyDTO entityToDTO(Reply reply) {
		
		ReplyDTO replyDTO = ReplyDTO.builder().rno(reply.getRno())
											  .bno(reply.getBoard().getBno())
											  .replyer(reply.getReplyer())
											  .content(reply.getContent())
											  .heart(reply.getHeart())
											  .regDate(reply.getRegDate())
											  .updateDate(reply.getUpdateDate())
											  .build();
		
		return replyDTO;
	}
	
}
