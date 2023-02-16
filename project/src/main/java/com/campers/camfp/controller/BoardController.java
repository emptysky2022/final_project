package com.campers.camfp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
		
	}
	
	@GetMapping("/register")
	public void register() {
		
	}

}
