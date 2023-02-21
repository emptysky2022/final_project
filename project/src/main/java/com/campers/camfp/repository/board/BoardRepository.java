package com.campers.camfp.repository.board;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.campers.camfp.entity.board.Board;
import com.campers.camfp.entity.member.Member;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardQuerydsl {
	
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
	
	@Query("select b, r from Board b left join Reply r on r.board = b where b.bno = :bno")
	List<Object[]> getBoardWithReply(@Param("bno") Long bno);
	
	@Modifying
	@Query("delete from Board b where b.bno = :bno and b.member.mno = :mno")
	void deleteBoard(Long bno, Long mno);
	

}
