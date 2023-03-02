package com.campers.camfp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campers.camfp.config.auth.PrincipalDetails;
import com.campers.camfp.dto.item.ItemDTO;
import com.campers.camfp.dto.page.PageRequestDTO;
import com.campers.camfp.dto.page.PageResultDTO;
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
	public void itemList() { //itemList.js에서 document.ready후 /list/data 호출
	}
	
	@GetMapping("/detail")
	public void viewDetail(Long ino, Model model) {
		ItemDTO itemDTO = itemService.getOne(ino);
		
		log.info("itemdto = " + itemDTO);
		model.addAttribute("detail", itemDTO);
	}
	
	@ResponseBody
	@GetMapping("/{ino}")
	public ResponseEntity<ItemDTO> getOneItem(@PathVariable Long ino){
		ItemDTO itemDTO = itemService.getOne(ino);
		
		return new ResponseEntity<>(itemDTO, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping("/register")
	public ResponseEntity<Long> registerItem(@RequestBody ItemDTO itemDTO, @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {		
		log.info("itemDTO = " + itemDTO);
		itemDTO.setMaker(principalDetails.getMember().getNickname());
		itemDTO.setMno(principalDetails.getMember().getMno());
		Long ino = itemService.register(itemDTO);
		
		return new ResponseEntity<>(ino, HttpStatus.OK);
	}
	
	@ResponseBody
	@PutMapping("/modify")
	public ResponseEntity<Long> modifyItem(@RequestBody ItemDTO itemDTO, @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
		itemDTO.setMaker(principalDetails.getMember().getNickname());
		itemDTO.setMno(principalDetails.getMember().getMno());
		Long ino = itemService.modify(itemDTO);
		
		return new ResponseEntity<>(ino, HttpStatus.OK);
	}
	
	@ResponseBody
	@DeleteMapping("/{ino}")
	public ResponseEntity<String> deleteReviewOfItem(@PathVariable Long ino){
		log.info("delete mapping : " + ino);
		
		itemService.remove(ino);
		
		return new ResponseEntity<>("아이템 삭제 성공", HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping("/list/data")
	public ResponseEntity<List<Object>> itemListWithCondition(PageRequestDTO pageRequestDTO, @AuthenticationPrincipal PrincipalDetails principalDetails, String category, String keyword, String type) {
		log.info("get list with condition : " + category + keyword + type);
		PageResultDTO<ItemDTO, Object[]> result = itemService.getListOfPage(pageRequestDTO,
				List.of(category, keyword, type));
		if(principalDetails != null) {
			return new ResponseEntity<>(List.of(result, principalDetails.getMember()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(List.of(result, ""), HttpStatus.OK);
		}
	}
	
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