package com.campers.camfp.repository.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.campers.camfp.dto.member.MemberDTO;
import com.campers.camfp.entity.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	//findBy까지는 규칙->Username은 문법
	//select*from member where name=?
	public Member findById(String username); //Jpa Query methods 검색 
	
	// SELECT * FROM member WHERE Id = ?1 and Nickname = ?2
		Optional<Member> findByIdAndNickname(String id, String nickname);
		
		//중복검사
		 @Query("SELECT COUNT(m.id) FROM Member m WHERE m.id = :id")
		    int countById(@Param("id") String id);
		 //닉네임
		 @Query("SELECT COUNT(m.nickname) FROM Member m WHERE m.nickname = :nickname")
		 int countByNickname(@Param("nickname")String nickname);
		//delete
		 @Modifying
		 @Transactional
		 public void deleteByMno(Long mno);
		 
		 
	@Query(value = "select *" +
		   " from member" +
		   " where id = :id",
		   nativeQuery = true)
	Object getMemberById(@Param("id") String id);

}
