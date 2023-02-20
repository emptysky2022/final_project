package com.campers.camfp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campers.camfp.config.auth.PrincipalDetails;
import com.campers.camfp.dto.item.ItemDTO;
import com.campers.camfp.dto.page.PageRequestDTO;
import com.campers.camfp.dto.page.PageResultDTO;
import com.campers.camfp.service.item.ItemService;
import com.querydsl.core.util.StringUtils;

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
		log.info(itemService.getListOfPage(pageRequestDTO, null));
		model.addAttribute("result", itemService.getListOfPage(pageRequestDTO, null));
	}
	
	@GetMapping("/detail")
	public void viewDetail(Long ino, Model model) {
		ItemDTO itemDTO = itemService.getOne(ino);
		
		log.info("itemdto = " + itemDTO);
		model.addAttribute("detail", itemDTO);
	}
	
	@ResponseBody
	@GetMapping("/list/data")
	public ResponseEntity<PageResultDTO<ItemDTO, Object[]>> itemListWithCondition(PageRequestDTO pageRequestDTO, String category, String keyword, String type) {
		log.info("get list with condition : " + category + keyword + type);
		PageResultDTO<ItemDTO, Object[]> result = itemService.getListOfPage(pageRequestDTO,
				List.of(category, keyword, type));
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("ROLE_MEMBER")
	@ResponseBody
	@GetMapping("/heart/{ino}")
	public ResponseEntity<Integer>getHeart(@PathVariable Long ino, @AuthenticationPrincipal PrincipalDetails principalDetails){
		log.info("click item heart");
		if(principalDetails != null) {
				
			int result = itemService.heartOfMember(ino);
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
	}
}
