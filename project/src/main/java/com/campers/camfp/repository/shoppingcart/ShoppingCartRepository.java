package com.campers.camfp.repository.shoppingcart;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campers.camfp.entity.shoppingcart.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>{

}
