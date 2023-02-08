package com.campers.camfp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campers.camfp.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{

}
