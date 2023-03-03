package com.campers.camfp.controller;

import java.awt.PageAttributes.MediaType;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campers.camfp.config.auth.PrincipalDetails;
import com.campers.camfp.config.type.TableType;
import com.campers.camfp.dto.board.BoardDTO;
import com.campers.camfp.dto.camp.CampCalenderDTO;
import com.campers.camfp.dto.camp.CampDTO;
import com.campers.camfp.dto.camp.CampReviewDTO;
import com.campers.camfp.dto.page.PageRequestDTO;
import com.campers.camfp.dto.page.PageResultDTO;
import com.campers.camfp.service.camp.CampService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/camp")
@Log4j2
@RequiredArgsConstructor
public class CampRestController {

	private final CampService campService;

	@PostMapping("/register")
	public ResponseEntity<String> campRegister(@RequestBody CampDTO dto,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		log.info(dto);

		Long mno = principalDetails.getMember().getMno();
		log.info(mno);

		if (mno == null) {
			return new ResponseEntity<String>("erro", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		dto.setMno(mno);
		campService.register(TableType.CAMP, dto);

		return new ResponseEntity<String>(dto.getName(), HttpStatus.OK);
	}
	
	@GetMapping(value = "list/{type}/{locations}/{page}/{size}")
	public ResponseEntity<List<Object>> getListByCamp(@PathVariable("page") int page,@PathVariable("size") int size,  @PathVariable("type") String[] type,
			@PathVariable("locations") String[] locations, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		List<CampDTO> campdtoList = new ArrayList<>();
		List<Double> avg = new ArrayList<>();
		
		PageRequestDTO dto = new PageRequestDTO();
		dto.setPage(page);
		dto.setSize(size);
		
		log.info(dto);
			
		 PageResultDTO<CampDTO, Object[]> boardDTO = campService.findManayDataOfCamp(dto, type, locations);

		boardDTO.getDtoList().forEach(value -> {
			avg.add(campService.countStar(value.getCno()));
		});
		
		log.info(campdtoList);

		return new ResponseEntity<>(List.of(boardDTO, principalDetails.getMember(),avg), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("campData/{cno}")
	public ResponseEntity<Object> getData(@PathVariable("cno") Long cno) {

		return new ResponseEntity(campService.findbyId(TableType.CAMP, cno), HttpStatus.OK);
	}

	@PutMapping("/modify")
	public ResponseEntity<Object> getData(@RequestBody CampDTO dto,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		dto.setMno(principalDetails.getMember().getMno());
		log.info(dto);
		campService.modify(TableType.CAMP, dto);

		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@DeleteMapping("/remove/{cno}")
	public ResponseEntity<Object> removeCamp(@PathVariable("cno") Long cno) {
		// remove 고려해야할 부분 있음

		campService.remove(TableType.CAMP, cno);
		return new ResponseEntity<Object>(HttpStatus.OK);

	}

	@PostMapping("calendar/register")
	public ResponseEntity<String> calendarRegister(@RequestBody CampCalenderDTO dto,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {

		String nickname = principalDetails.getMember().getNickname();

		log.info("컨트롤러 데이터");
		log.info(dto);

		if (nickname == null) {
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		dto.setReservationer(nickname);
		campService.register(TableType.CAMPCALENDER, dto);

		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	@GetMapping(value = "reply/list/{cno}")
	public ResponseEntity<List<Object>> getListByReview(@PathVariable("cno") Long cno,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("탔음");
		List<CampReviewDTO> reviews = new ArrayList<>();
		String[] datas = { "별점순" };
		campService.findDataOfCamp(TableType.CAMPREVIEW, cno, datas).forEach(value -> {
			reviews.add((CampReviewDTO) value);
		});

		log.info(reviews);
		return new ResponseEntity<>(List.of(reviews, principalDetails.getMember()), HttpStatus.OK);
	}

	@GetMapping("/reply/one/{crno}")
	public ResponseEntity<CampReviewDTO> getOneByReview(@PathVariable("crno") Long crno) {
		Object data = campService.findbyId(TableType.CAMPREVIEW, crno);

		CampReviewDTO value = (CampReviewDTO) data;

		return new ResponseEntity<CampReviewDTO>(value, HttpStatus.OK);

	}

	@PutMapping("/reply/modify")
	public ResponseEntity<String> updateReview(@RequestBody CampReviewDTO dto,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		log.info(dto);
		dto.setReviewer(principalDetails.getMember().getNickname());
		campService.modify(TableType.CAMPREVIEW, dto);
		return new ResponseEntity<String>("성공", HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("review/register")
	public ResponseEntity<String> reviewRegister(@RequestBody CampReviewDTO dto,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		log.info(dto);
		String nickname = principalDetails.getMember().getNickname();
		log.info(principalDetails.getMember());
		if (nickname == null) {
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		dto.setReviewer(principalDetails.getMember().getNickname());
		campService.register(TableType.CAMPREVIEW, dto);
		return new ResponseEntity(nickname, HttpStatus.OK);
	}

	@DeleteMapping("review/{crno}")
	public ResponseEntity<Long> reviewRemove(@PathVariable("crno") Long crno) {
		log.info(crno);
		campService.remove(TableType.CAMPREVIEW, crno);

		return new ResponseEntity<Long>(HttpStatus.OK);
	}

}
