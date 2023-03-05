package com.campers.camfp.repository.heart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.campers.camfp.config.type.ProductType;
import com.campers.camfp.entity.heart.Heart;

public interface HeartListRepository extends JpaRepository<Heart, Long>, HeartQuerydsl {
	
	@Query("select h from Heart h left join Member m on h.member=m where m.mno=:mno group by h")
	List<Heart> getHeartByMember(Long mno);
	
	@Query("select h from Heart h left join Member m on h.member=m where m.mno=:mno and h.productType=:productType group by h")
	List<Heart> getHeartByProductType(Long mno, ProductType productType);

	Long countHeartByProductNumAndProductType(Long productNum, ProductType productType);

}
