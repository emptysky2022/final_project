package com.campers.camfp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.campers.camfp.dto.item.ItemDTO;
import com.campers.camfp.service.item.ItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
	
	private final ItemService itemService;

	@GetMapping("/list")
	public void itemList(Model model) {
		List<ItemDTO> itemLists = new ArrayList<>();
		for(Long i = 2L; i <= 10L; i++) {
			itemLists.add(itemService.getOne(i));
		}
		
		log.info(itemLists);
		model.addAttribute("result", itemLists);
	}
	
}
