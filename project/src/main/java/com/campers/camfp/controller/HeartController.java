package com.campers.camfp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.campers.camfp.config.auth.PrincipalDetails;
import com.campers.camfp.config.type.ProductType;
import com.campers.camfp.config.type.TableType;
import com.campers.camfp.dto.heart.HeartDTO;
import com.campers.camfp.service.board.BoardService;
import com.campers.camfp.service.camp.CampService;
import com.campers.camfp.service.heart.HeartService;
import com.campers.camfp.service.item.ItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/heart")
@Log4j2
@RequiredArgsConstructor
public class HeartController {

	private final HeartService heartService;
	private final ItemService itemService;
	private final CampService campService;
	private final BoardService boardService;

	@PostMapping("/getOne")
	public ResponseEntity<HeartDTO> getHeart(@RequestBody HeartDTO dto,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		log.info("받은값");
		log.info(dto);
		HeartDTO value;
		dto.setMno(principalDetails.getMember().getMno());
		if (heartService.findHeart(dto) == null) {
			log.info("null 리턴");
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		value = heartService.findHeart(dto);
		log.info("최종리턴");
		log.info(value);
		return new ResponseEntity<>(value, HttpStatus.OK);
	}

	@PostMapping("/getList")
	public ResponseEntity<Boolean> getList(@RequestBody HeartDTO dto,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		log.info(dto);
		dto.setMno(principalDetails.getMember().getMno());
		return null;
	}
	
	@GetMapping("/get/{productType}")
	public ResponseEntity<List<Object[]>> getListByProductType(@PathVariable String productType, @AuthenticationPrincipal PrincipalDetails principalDetails){
		log.info("producttype 따라 좋아요 목록");
		log.info(productType);
		List<HeartDTO> heartDTOs = heartService.getListOfProductType(principalDetails.getMember().getMno(), ProductType.valueOf(productType));
		log.info(heartDTOs);
		List<Object[]> result = new ArrayList<>();
		switch(productType) {
		case "BOARD": 
			heartDTOs.forEach(dto -> {
				result.add(new Object[] {
					dto, boardService.read(dto.getProductNum())
				});
			});
			break;
		case "CAMP": 
			heartDTOs.forEach(dto -> {
				result.add(new Object[] {
						dto, campService.findbyId(TableType.CAMP, dto.getProductNum())
				});
			});
			break;
		case "ITEM": 
			heartDTOs.forEach(dto -> {
				result.add(new Object[] {
						dto, itemService.getOne(dto.getProductNum())
				});
			});
			break;
		}
		log.info(result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/save")
	public ResponseEntity<HeartDTO> saveHeart(@RequestBody HeartDTO dto,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		dto.setMno(principalDetails.getMember().getMno());
		log.info(dto);
		return new ResponseEntity<HeartDTO>(heartService.saveHeart(dto), HttpStatus.OK);
	}

	@DeleteMapping("/remove")
	public ResponseEntity<Boolean> removeHeart(@RequestBody HeartDTO dto,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		log.info(dto);
		Boolean val = true;
		dto.setMno(principalDetails.getMember().getMno());
		log.info(dto);

		heartService.removeHeart(dto);

		return new ResponseEntity<Boolean>(val, HttpStatus.OK);
	}

}
