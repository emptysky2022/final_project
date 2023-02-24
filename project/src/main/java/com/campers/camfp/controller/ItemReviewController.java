package com.campers.camfp.controller;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.campers.camfp.config.auth.PrincipalDetails;
import com.campers.camfp.dto.UploadResultDTO;
import com.campers.camfp.dto.item.ItemReviewDTO;
import com.campers.camfp.service.item.ItemReviewService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/review")
public class ItemReviewController {
	
	private final ItemReviewService itemReviewService;
	
	//파일이 저장된 디렉토리의 기본 위치
	@Value("${com.campers.camfp.upload.path}")
	String uploadPath;
	
	@GetMapping("/detail/{ino}")
	public ResponseEntity<List<Object>> getReviewsOfItem(@PathVariable Long ino, @AuthenticationPrincipal PrincipalDetails principalDetails){
		List<ItemReviewDTO> result = itemReviewService.getReviewOfItem(ino);
		if(principalDetails != null) {
			return new ResponseEntity<>(List.of(result, principalDetails.getMember()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(List.of(result, ""), HttpStatus.OK);
		}
	}
	
	@GetMapping("/{irno}")
	public ResponseEntity<ItemReviewDTO> getOneReview(@PathVariable Long irno){
		ItemReviewDTO itemReviewDTO = itemReviewService.getOne(irno);
		
		return new ResponseEntity<ItemReviewDTO>(itemReviewDTO, HttpStatus.OK);
	}
	
	@GetMapping("/heart/{irno}")
	public ResponseEntity<Integer> clickHeart(@PathVariable Long irno){
		log.info("click item review heart");
		int result = itemReviewService.heartOfMember(irno);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Long> registerReviewOfItem(@RequestBody ObjectNode obj, @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception{
		//RequestBody는 단일개체만 가능, ObjectNode로 값 가져옴(ItemReviewDTO, ImageURL)
		log.info(obj);
		//ObjectNode의 값을 타입변경해줄 mapper
		ObjectMapper mapper = new ObjectMapper();
		//review라는 이름으로 넘어온 ItemReviewDTO의 JSON data를 ItemReviewDTO로 받아서 넘겨줌
		ItemReviewDTO itemReviewDTO = mapper.treeToValue(obj.get("review"), ItemReviewDTO.class);
		log.info("itemReview : " + itemReviewDTO);
		
		//ImageURL이 여러개이면 List타입으로 받아야 하기 때문에 reader 선언
		ObjectReader reader = mapper.readerFor(new TypeReference<List<String>>() {});
		
		//readValue로 값 읽어서 List에 저장
		List<String> imageURLList = reader.readValue(obj.get("image"));
		for(String imageURL : imageURLList) {
			itemReviewDTO.setCapture(imageURL);
		}
		
		itemReviewDTO.setReviewer(principalDetails.getMember().getNickname());
		itemReviewService.register(itemReviewDTO);
		
		return new ResponseEntity<>(itemReviewDTO.getIno(), HttpStatus.OK);
	}

	@RequestMapping(value = "/modify", method = RequestMethod.PUT)
	public ResponseEntity<Long> modifyReviewOfItem(@RequestBody ObjectNode obj, @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception{
		//RequestBody는 단일개체만 가능, ObjectNode로 값 가져옴(ItemReviewDTO, ImageURL)
		log.info(obj);
		//ObjectNode의 값을 타입변경해줄 mapper
		ObjectMapper mapper = new ObjectMapper();
		//review라는 이름으로 넘어온 ItemReviewDTO의 JSON data를 ItemReviewDTO로 받아서 넘겨줌
		ItemReviewDTO itemReviewDTO = mapper.treeToValue(obj.get("review"), ItemReviewDTO.class);
		log.info("itemReview : " + itemReviewDTO);
		
		//ImageURL이 여러개이면 List타입으로 받아야 하기 때문에 reader 선언
		ObjectReader reader = mapper.readerFor(new TypeReference<List<String>>() {});
		
		//readValue로 값 읽어서 List에 저장
		List<String> imageURLList = reader.readValue(obj.get("image"));
		for(String imageURL : imageURLList) {
			itemReviewDTO.setCapture(imageURL);
		}
		
		itemReviewDTO.setReviewer(principalDetails.getMember().getNickname());
		Long ino = itemReviewService.modify(itemReviewDTO);
		
		return new ResponseEntity<>(ino, HttpStatus.OK);
	}
	
<<<<<<< HEAD
	
=======
	@DeleteMapping("/{irno}")
	public ResponseEntity<String> deleteReviewOfItem(@PathVariable Long irno){
		log.info("delete mapping : " + irno);
		
		itemReviewService.remove(irno);
		
		return new ResponseEntity<>("리뷰 삭제 성공", HttpStatus.OK);
	}
>>>>>>> 59e453cbe9f9a48f400e2c480b77333f299b270a
	
}
