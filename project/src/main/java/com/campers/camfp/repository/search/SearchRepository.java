package com.campers.camfp.repository.search;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campers.camfp.entity.item.Item;

public interface SearchRepository extends JpaRepository<Item, Long>{
	
//	@Query("select m, h from Member m left join ItemHistory h on h.member=m where m.id = :id")
//	
//	List<Object[]> getBoardWithReply(@Param("bno") Long bno);
	
}
