package com.campers.camfp.repository.history;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.campers.camfp.entity.history.History;

public interface HistoryRepository extends JpaRepository<History, Long>{

	@Query("select h from History h where h.member.mno = :mno")
	List<History> getHistoryOfMember(Long mno);
}
