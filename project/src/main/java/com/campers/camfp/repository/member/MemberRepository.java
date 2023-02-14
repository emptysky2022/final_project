package com.campers.camfp.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.campers.camfp.entity.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	//findBy까지는 규칙->Username은 문법
	//select*from member where name=?
	public Member findByName(String username); //Jpa Query methods 검색 
	
	@Query(value = "select *" +
		   " from member" +
		   " where id = :id",
		   nativeQuery = true)
	Object getMemberById(@Param("id") String id);
	
}
