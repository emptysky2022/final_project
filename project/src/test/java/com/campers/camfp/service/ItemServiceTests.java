package com.campers.camfp.service;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.campers.camfp.dto.item.ItemDTO;
import com.campers.camfp.service.item.ItemService;

@SpringBootTest
public class ItemServiceTests {
	
	@Autowired
	private ItemService itemService;

	@Test
	public void insertDummies() {
		System.out.println("insert item test");
		IntStream.rangeClosed(1, 100).forEach(i -> {
			ItemDTO itemDTO = ItemDTO.builder()
							.name("테스트상품"+i)
							.mno(1L)
							.brand("캠프피")
							.maker("코리아")
							.category1("조명")
							.price(i*1000)
							.count(i)
							.build();
			
			itemService.register(itemDTO);
		});
	}
	
	@Test
	public void selectOneDummy(){
		System.out.println("select item test");
		Long ino = 2L;
		itemService.getOne(ino);
	}
	
	@Test
	public void selectAllDummies() {
		System.out.println("select all item test");
		
		itemService.getList();
	}
	
	@Test
	public void modifyDummy() {
		System.out.println("modify item test");
		ItemDTO itemDTO = ItemDTO.builder()
						.ino(2L)
						.name("수정테스트")
						.brand("수정캠프피")
						.maker("차이나")
						.build();
		itemService.modify(itemDTO);
	}
	
	@Test
	public void removeDummy() {
		System.out.println("remove item test");
		Long ino = 1L;
		itemService.remove(ino);
	}
}
