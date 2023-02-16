package com.campers.camfp.service;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.campers.camfp.dto.shopingcart.ShoppingCartDTO;
import com.campers.camfp.service.shoppingcart.ShoppingCartService;

@SpringBootTest
public class ShoppingCartServiceTests {

	@Autowired
	private ShoppingCartService shoppingCartService;

	@Test
	public void insertDummies() {
		IntStream.rangeClosed(1, 50).forEach(i -> {
			ShoppingCartDTO shoppingCartDTO = ShoppingCartDTO.builder()
												.amount(2)
												.mno(2L)
												.ino((long)i)
												.build();
			shoppingCartService.register(shoppingCartDTO);
		});
	}

	@Test
	public void modifyDummy() {
		ShoppingCartDTO shoppingCartDTO = shoppingCartService.getOne(2L);
		shoppingCartDTO.setAmount(3);
		shoppingCartService.modify(shoppingCartDTO);

	}

	@Test
	public void removeDummy() {
		shoppingCartService.remove(1L);
	}
}