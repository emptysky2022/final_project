package com.campers.camfp.repository.camp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campers.camfp.config.type.TableType;

public interface CampQuerydsl {

	
	 /** 
	  * @param table 변환할 table 명 table <br>
	  * @param address 찾을 주소 ex) 경기도 , 강원도 , 서울 <br>
	  * @return List<?> List WildCard
	  */
	public List<?> findByAddress(TableType table, String address);
	
	 /** 
	  * @param table 변환할 table 명 table <br>
	  * @param count 1 ~ count 개 까지 받아오기 <br>
	  * @return List<?> List WildCard
	  */
	public List<?> findHeartRank(TableType table, int count);
	
	
	 /** 
	  * @param table 찾을 table 명 <br>
	  * @param mno member 부모번호 <br>
	  * @return List<?> List WildCard
	  */
	public List<?> findDataOfMember(TableType table, Long mno);
	
	 /** 
	  * @param table 찾을 table 명 <br>
	  * @param cno camp 부모번호 <br>
	  * @return List<?> List WildCard
	  */
	public List<?> findDataOfCamp(TableType table, Long cno);
	
}
	