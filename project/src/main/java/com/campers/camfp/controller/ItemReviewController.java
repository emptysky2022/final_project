package com.campers.camfp.controller;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
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
	public ResponseEntity<List<Object>> getReviewOfItem(@PathVariable Long ino, @AuthenticationPrincipal PrincipalDetails principalDetails){
		List<ItemReviewDTO> result = itemReviewService.getReviewOfItem(ino);
		if(principalDetails != null) {
			return new ResponseEntity<>(List.of(result, principalDetails.getMember()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(List.of(result, ""), HttpStatus.OK);
		}
	}
	
	@GetMapping("/heart/{irno}")
	public ResponseEntity<Integer> clickHeart(@PathVariable Long irno){
		log.info("click item review heart");
		int result = itemReviewService.heartOfMember(irno);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Long> registerReviewOfItem(@RequestBody ObjectNode obj, @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception{
		//문제 : @RequestBody는 단일 객체만 처리할수 있다. 여러개 쓰는건 불가능하다. 
//		for(UploadResultDTO image : imageURL) {
//			itemReviewDTO.setCapture(image.getImageURL());
//		}
//		
//		itemReviewDTO.setReviewer(principalDetails.getMember().getNickname());
//		itemReviewService.register(itemReviewDTO);
//		
//		return new ResponseEntity<>(itemReviewDTO.getIno(), HttpStatus.OK);
		
		//1차시도 : 두개의 객체를 각각의 Json타입 변수로 들고와 저장한다.(근데 가져올 타입을 몰라서 Model을 넣어봄)
//		ItemReviewDTO itemReviewDTO = (ItemReviewDTO) model.getAttribute("review");
//		log.info("register controller dto : " + model.getAttribute("review"));
//		log.info("register image dto : " + model.getAttribute("image"));
//		return new ResponseEntity<>(itemReviewDTO.getIno(), HttpStatus.OK);
		
		//2차시도 : ObjectNode를 통해 가져온 값들을 매핑한다.
		log.info(obj);
		ObjectMapper mapper = new ObjectMapper();
		ItemReviewDTO itemReviewDTO = mapper.treeToValue(obj.get("review"), ItemReviewDTO.class);
		log.info("itemReview : " + itemReviewDTO);
//		log.info("imageDTO : " + mapper.treeToValue(obj.get("image"), UploadResultDTO[].class));
//		List<UploadResultDTO> imageDTO = Arrays.asList(mapper.(obj.get("image"), UploadResultDTO[].class));
		
		ObjectReader reader = mapper.readerFor(new TypeReference<List<String>>() {});
		log.info("여긴 됨");
		
		List<String> imageURLList = reader.readValue(obj.get("image"));
		log.info("여긴 안됨");
//		
//		for(UploadResultDTO imageDTO : imageDTOList) {
//			itemReviewDTO.setCapture(imageDTO.getImageURL());
//			log.info("포문 안에 있음");
//		}
		for(String imageURL : imageURLList) {
			itemReviewDTO.setCapture(imageURL);
			log.info("포문 안에 있음");			
		}
		
		log.info("아님 여기?");
		
		itemReviewDTO.setReviewer(principalDetails.getMember().getNickname());
		itemReviewService.register(itemReviewDTO);
		
		return new ResponseEntity<>(itemReviewDTO.getIno(), HttpStatus.OK);
	}
	
}
