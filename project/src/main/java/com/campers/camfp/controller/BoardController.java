package com.campers.camfp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campers.camfp.dto.board.BoardDTO;
import com.campers.camfp.dto.page.PageRequestDTO;
import com.campers.camfp.service.board.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/sample/")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	
	@GetMapping("/list")
	public void list(PageRequestDTO pageRequestDTO, Model model) {
		log.info("list......" + pageRequestDTO);
		model.addAttribute("result", boardService.getList(pageRequestDTO));
		
		log.info("model : " + model);
		
	}
	
//	@ResponseBody
//	@GetMapping("/register")
//	public void register() {
//		log.info("register get......... ");
//	}
	
	@ResponseBody
	@PostMapping("")
	public ResponseEntity<Long> register(BoardDTO boardDTO) {
		
		log.info("controller....... " + boardDTO);
		
		Long bno = boardService.register(boardDTO);
		
		return new ResponseEntity<Long>(bno, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value="/list/{bno}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BoardDTO> read(@PathVariable("bno") Long bno) {		
		log.info("bno : " + bno);
		
		BoardDTO boardDTO2 = boardService.read(bno);
		
		
		log.info("-----------dto---------- : " + boardDTO2);
		return new ResponseEntity<BoardDTO>(boardDTO2, HttpStatus.OK);
	}
}
