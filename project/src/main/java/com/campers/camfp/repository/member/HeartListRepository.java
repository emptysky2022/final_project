package com.campers.camfp.repository.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.campers.camfp.entity.heartlist.HeartList;

public interface HeartListRepository extends JpaRepository<HeartList, Long> {
	
	@Query("select h from HeartList h left join Member m on h.member=m where m.mno=:mno group by h")
	List<HeartList> getHeartByMember(Long mno);

}
