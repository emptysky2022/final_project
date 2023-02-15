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
import com.campers.camfp.dto.page.PageRequestDTO;
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
	public void itemList(PageRequestDTO pageRequestDTO, Model model) {
		log.info(itemService.getListOfPage(pageRequestDTO));
		model.addAttribute("result", itemService.getListOfPage(pageRequestDTO));
	}
	
	@GetMapping("/item")
	public void viewDetail(Long ino, Model model) {
		ItemDTO itemDTO = itemService.getOne(ino);
		
		log.info("itemdto = " + itemDTO);
		model.addAttribute("detail", itemDTO);
	}
	
}
