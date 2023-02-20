package com.campers.camfp.repository.board;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.campers.camfp.dto.board.ReplyDTO;
import com.campers.camfp.entity.board.Board;
import com.campers.camfp.entity.board.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>{
	
	// ReplyServiceImpl - getListOfBoard()에서 쓸건데 어떻게 쓰는거람..
	@EntityGraph(attributePaths = {"board"}, type = EntityGraphType.FETCH)
	List<Reply> findByBoard(Board board);

	// 게시글로 댓글 가져오기 - Rno 기준
	List<Reply> getRepliesByBoard(Board board);
	
}
