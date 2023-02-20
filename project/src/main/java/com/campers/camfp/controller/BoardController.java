package com.campers.camfp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.campers.camfp.config.auth.PrincipalDetails;
import com.campers.camfp.dto.board.BoardDTO;
import com.campers.camfp.dto.board.ReplyDTO;
import com.campers.camfp.dto.page.PageRequestDTO;
import com.campers.camfp.dto.page.PageResultDTO;
import com.campers.camfp.service.board.BoardService;
import com.campers.camfp.service.board.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/sample/")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
   
   private final BoardService boardService;
   private final ReplyService replyService;
   
   @GetMapping("/list")
   public void list(PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
      log.info("list......" + pageRequestDTO);
      model.addAttribute("result", boardService.getList(pageRequestDTO));
      model.addAttribute("memInfo", principalDetails.getMember());
      
      log.info("model : " + model);
      log.info("로그인 정보 : " + principalDetails.getMember());
      
   }
   
   @ResponseBody
   @GetMapping(value="/list/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<List<Object>> getList(PageRequestDTO pageRequestDTO, @AuthenticationPrincipal PrincipalDetails principalDetails) {
      
      PageResultDTO<BoardDTO, Object[]> boardDTO = boardService.getList(pageRequestDTO);
      
      log.info("refresh : " + boardDTO);
      
      return new ResponseEntity<>(List.of(boardDTO), HttpStatus.OK);
   }
   
   
   @GetMapping("/register")
   public void register() {
      log.info("register get......... ");
   }
   
   @PostMapping("/register")
   public String register(BoardDTO boardDTO) {
      log.info("registerDTO...... : " + boardDTO);
      
      boardService.register(boardDTO);
      
      return "redirect:/sample/list";
   }
   
   @ResponseBody
   @GetMapping(value="/list/{bno}", produces=MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<List<Object>> read(@PathVariable("bno") Long bno, @AuthenticationPrincipal PrincipalDetails principalDetails) {      
      log.info("bno : " + bno);
      
      BoardDTO boardDTO = boardService.read(bno);
      List<ReplyDTO> replyDTO = replyService.getListOfBoard(bno);
      
      log.info("-----------boardDTO---------- : " + boardDTO);
      log.info("-----------replyDTO---------- : " + replyDTO);
      return new ResponseEntity<List<Object>>(List.of(boardDTO, replyDTO, principalDetails.getMember()), HttpStatus.OK);
   }
   
   @ResponseBody
   @PutMapping(value="/update/{bno}")
   public ResponseEntity<String> modify(@RequestBody BoardDTO boardDTO, @AuthenticationPrincipal PrincipalDetails principalDetails) {
      
      log.info("update DTO : " + boardDTO);
      
      boardService.modify(boardDTO);
      
      return new ResponseEntity<String>("success", HttpStatus.OK);
   }
   
   @ResponseBody
   @DeleteMapping(value = "/list/remove/{bno}")
   public ResponseEntity<String> remove(Long bno) {
      
      log.info("delete : " + bno);
      
      boardService.removeWithReplies(bno);
      
      return new ResponseEntity<String>("success", HttpStatus.OK);      
      
      // remove 되지않음. 확인 및 수정 필요함.
   }
   
}