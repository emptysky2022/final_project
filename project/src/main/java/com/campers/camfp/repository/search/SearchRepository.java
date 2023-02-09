package com.campers.camfp.repository.search;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.campers.camfp.entity.Item;

public interface SearchRepository extends JpaRepository<Item, Long>{
	
//	@Query("select m, h from Member m left join ItemHistory h on h.member=m where m.id = :id")
//	
//	List<Object[]> getBoardWithReply(@Param("bno") Long bno);
	
}
