package com.campers.camfp.repository.camp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campers.camfp.config.type.TableType;
import com.campers.camfp.entity.camp.Camp;
import com.querydsl.jpa.JPQLQuery;

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
	public List<?> findHeartOrCountRank(TableType table, int count, String findType);
	
	
	 /** 
	  * @param table 찾을 table 명 <br>
	  * @param mno member 부모번호 <br>
	  * @return List<?> List WildCard
	  */
	public List<?> findDataOfMember(TableType table, Long mno);
	
	 /** 
	  * @param table 찾을 table 명 <br>
	  * @param cno camp 부모번호 <br>
	  * @param findDatas 찾을 내용 <br>
	  * @return List<?> List WildCard
	  */
	public List<?> findDataOfCamp(TableType table, Long cno, String[] findDatas);
	
	 /** 
	  * @param findDatas 찾을 data 명 <br>
	  * @param findLocations camp 지역 <br>
	  * @return List<Camp> 부득이하게 camp 만 너무 많은 로직이 들어간 데이터가 필요해서 camp 형식으로 작성함.
	  */
	public List<Camp> findManayDataOfCamp(String[] findDatas, String[] findLocations);
	
	 /** 
	  * @param addData 더할 데이터 ex)count , heart, star 명 <br>
	  * @param no 해당 캠프타입 번호 <br>
	  * @return List<?> List WildCard
	  */
	public void addData(TableType table, Long no, String addData);
	
	
	public int findData(TableType table, Long no, String findData);
	
}
	