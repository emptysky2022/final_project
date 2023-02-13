package com.campers.camfp.repository.item;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campers.camfp.entity.item.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{

}
