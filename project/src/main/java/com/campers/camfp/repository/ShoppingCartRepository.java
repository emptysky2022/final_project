package com.campers.camfp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campers.camfp.entity.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>{

}
