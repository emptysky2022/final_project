package com.campers.camfp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.campers.camfp.repository.item.ItemRepository;

@SpringBootTest
public class ItemRepositoryTests {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Test
	public void ItemQueryCategoryTest() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("count").descending().and(Sort.by("ino").descending()));
		itemRepository.findBySearch("", "", pageable);
		
	}
	
}
