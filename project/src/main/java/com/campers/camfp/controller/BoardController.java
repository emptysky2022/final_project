package com.campers.camfp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@ResponseBody
	@GetMapping("/register")
	public void register() {
		log.info("register get......... ");
	}
	
	@ResponseBody
	@PutMapping("/register")
	public String registerPost(BoardDTO boardDTO /* , Model model */) {
		log.info("boardDTO....... " + boardDTO);
		
		String nick = boardService.register(boardDTO);
		
		log.info("bno : " + nick);
		
		return "redirect:/sample/list";
	}
	
	@ResponseBody
	@GetMapping(value="/read/{bno}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BoardDTO> read(@PathVariable("bno") Long bno) {		
		log.info("bno : " + bno);
		
		BoardDTO boardDTO2 = boardService.get(bno);
		
		
		log.info("-----------dto---------- : " + boardDTO2);
		return new ResponseEntity<BoardDTO>(boardDTO2, HttpStatus.OK);
	}
	
	
	
}
