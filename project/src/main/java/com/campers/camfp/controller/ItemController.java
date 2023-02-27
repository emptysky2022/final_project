package com.campers.camfp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.campers.camfp.config.auth.PrincipalDetails;
import com.campers.camfp.dto.item.ItemDTO;
import com.campers.camfp.dto.item.ItemReviewDTO;
import com.campers.camfp.dto.page.PageRequestDTO;
import com.campers.camfp.dto.page.PageResultDTO;
import com.campers.camfp.service.item.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
	public ResponseEntity<Long> registerItem(@RequestBody ObjectNode obj, @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
		//RequestBody는 단일개체만 가능, ObjectNode로 값 가져옴(ItemDTO, ImageURL)
		log.info(obj);
		//ObjectNode의 값을 타입변경해줄 mapper
		ObjectMapper mapper = new ObjectMapper();
		//review라는 이름으로 넘어온 ItemReviewDTO의 JSON data를 ItemReviewDTO로 받아서 넘겨줌
		ItemDTO itemDTO = mapper.treeToValue(obj.get("item"), ItemDTO.class);
		log.info("itemReview : " + itemDTO);
		
		//ImageURL이 여러개이면 List타입으로 받아야 하기 때문에 reader 선언
		ObjectReader reader = mapper.readerFor(new TypeReference<List<String>>() {});
		
		//readValue로 값 읽어서 List에 저장
		List<String> imageURLList = reader.readValue(obj.get("image"));
		String imageURLs = null;
		for(String imageURL : imageURLList) {
			imageURLs += imageURL + ",";
		}
		itemDTO.setThumbnail(imageURLs);
		
		itemDTO.setMaker(principalDetails.getMember().getNickname());
		itemDTO.setMno(principalDetails.getMember().getMno());
		Long ino = itemService.register(itemDTO);
		
		return new ResponseEntity<>(ino, HttpStatus.OK);
	}
	
	@ResponseBody
	@PutMapping("/modify")
	public ResponseEntity<Long> modifyItem(@RequestBody ObjectNode obj, @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
		//RequestBody는 단일개체만 가능, ObjectNode로 값 가져옴(ItemReviewDTO, ImageURL)
		log.info(obj);
		//ObjectNode의 값을 타입변경해줄 mapper
		ObjectMapper mapper = new ObjectMapper();
		//review라는 이름으로 넘어온 ItemReviewDTO의 JSON data를 ItemReviewDTO로 받아서 넘겨줌
		ItemDTO itemDTO = mapper.treeToValue(obj.get("item"), ItemDTO.class);
		log.info("item : " + itemDTO);
		
		if(itemDTO.getThumbnail() == null) {
			//ImageURL이 여러개이면 List타입으로 받아야 하기 때문에 reader 선언
			ObjectReader reader = mapper.readerFor(new TypeReference<List<String>>() {});
			
			//readValue로 값 읽어서 List에 저장
			List<String> imageURLList = reader.readValue(obj.get("image"));
			for(String imageURL : imageURLList) {
				log.info("어디서 안되는거야");
				itemDTO.setThumbnail(imageURL);
			}
		}
		
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
