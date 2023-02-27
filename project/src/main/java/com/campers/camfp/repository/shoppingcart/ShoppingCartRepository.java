package com.campers.camfp.repository.shoppingcart;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.campers.camfp.entity.shoppingcart.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>{

	@Query("select c from ShoppingCart c left join Item i on c.ino = i.ino where c.member.mno = :mno")
	Optional<List<ShoppingCart>> findCartOfMember(Long mno);
	
}
