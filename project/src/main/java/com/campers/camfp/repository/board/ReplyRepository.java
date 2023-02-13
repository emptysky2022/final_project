package com.campers.camfp.repository.board;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.campers.camfp.entity.board.Board;
import com.campers.camfp.entity.board.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>{
	
	// ReplyServiceImpl - getListOfBoard()에서 쓸건데 어떻게 쓰는거람..
	@EntityGraph(attributePaths = {"board"}, type = EntityGraphType.FETCH)
	List<Reply> findByBoard(Board board);

	
}
