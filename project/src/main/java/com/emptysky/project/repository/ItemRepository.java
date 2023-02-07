package com.emptysky.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emptysky.project.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{

}
