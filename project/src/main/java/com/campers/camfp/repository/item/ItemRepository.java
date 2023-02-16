package com.campers.camfp.repository.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.campers.camfp.entity.item.Item;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemQuerydsl{

	@Query("select i, m from Item i left join i.member m group by i")
	Page<Object[]> getListWithPage(Pageable pageable);

}
