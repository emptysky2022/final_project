package com.campers.camfp.service.board;

import java.util.function.Function;

import javax.transaction.Transactional;

import com.campers.camfp.config.type.ProductType;
import com.campers.camfp.repository.heart.HeartListRepository;
import com.campers.camfp.repository.heart.HeartQuerydsl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.campers.camfp.dto.board.BoardDTO;
import com.campers.camfp.dto.page.PageRequestDTO;
import com.campers.camfp.dto.page.PageResultDTO;
import com.campers.camfp.entity.board.Board;
import com.campers.camfp.entity.member.Member;
import com.campers.camfp.repository.board.BoardRepository;
import com.campers.camfp.repository.board.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

   private final BoardRepository boardRepository;
   private final ReplyRepository replyRepository;
   private final HeartListRepository heartListRepository;

   @Override
   public Long register(BoardDTO boardDTO) {

      log.info("BoardDTO---------------");
      log.info(boardDTO);
      log.info("BoardDTO---------------");
      
      Board enBoard = dtoToEntity(boardDTO);
      log.info(enBoard);
      
      boardRepository.save(enBoard);
      
      return enBoard.getBno();
   }

   @Override
   public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
      
      Pageable pageable = pageRequestDTO.getPageable(Sort.by("bno").descending());

      log.info("pageRequestDTO : " + pageRequestDTO);
      log.info("pageable : " + pageable);
      
      Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board) en[0], (Member) en[1], (Long) en[2], (Long) en[3]));
      
      // 검색 조건 추가를 위해 Querydsl로 변경함
      Page<Object[]> result = boardRepository.searchPage(pageRequestDTO.getType(), pageRequestDTO.getKeyword(), pageRequestDTO.getCategory(),pageable);
      
//      Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
      
      return new PageResultDTO<>(result, fn);
   }
   
   @Override
   public BoardDTO read(Long bno) {
      
      Object result = boardRepository.getBoardByBno(bno);
      Long heartCount = heartListRepository.countHeartByProductNumAndProductType(bno, ProductType.BOARD);
      Object[] arr = (Object[]) result;
      
      ((Board) arr[0]).increseCount();
      boardRepository.save((Board) arr[0]);
      
      return entityToDTO((Board) arr[0], (Member) arr[1], (Long) arr[2], heartCount);
   }

   @Transactional
   @Override
   public void removeWithReplies(BoardDTO boardDTO) {
      
      System.out.println(boardDTO);
      
      replyRepository.deleteReply(boardDTO.getBno());
      boardRepository.deleteBoard(boardDTO.getBno(), boardDTO.getMno());
   }

   @Override
   public void modify(BoardDTO boardDTO) {
      
      Board board = boardRepository.findById(boardDTO.getBno()).get();
      
      if(board != null) {
         board.change(boardDTO.getTitle(), boardDTO.getContent());
      }
      
      log.info("modify board : " + board);
      
      boardRepository.save(board);
   }

}