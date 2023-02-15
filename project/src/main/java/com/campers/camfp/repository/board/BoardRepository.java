package com.campers.camfp.repository.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.campers.camfp.entity.board.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
	
	@Query(value="select b, m, count(r) " +
			 " from Board b " +
			 " left join b.member m " +
			 " left join Reply r on r.board = b " +
			 " group by b",
			 countQuery = "select count(b) from Board b")
	Page<Object[]> getBoardWithReplyCount(Pageable pageable);
	
	@Query("select b, m, count(r) " + 
			   " from Board b left join b.member m " + 
			   " left outer join Reply r on r.board = b " +
			   " where b.bno = :bno")
	Object getBoardByBno(@Param("bno") Long bno);
	
	

}
