package com.campers.camfp.service.board;

import java.util.function.Function;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.campers.camfp.dto.board.BoardDTO;
import com.campers.camfp.dto.board.ReplyDTO;
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

	public final BoardRepository boardRepository;
	public final ReplyRepository replyRepository;
	
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
		
		Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board) en[0], (Member) en[1], (Long) en[2]));

		Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
		
		
		return new PageResultDTO<>(result, fn);
	}
	
//	public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
//		
//		Pageable pageable = pageRequestDTO.getPageable(Sort.by("bno").descending());
//		
//		log.info("pageRequestDTO : " + pageRequestDTO);
//		
//		Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board) en[0], (Member) en[1], (Long) en[2]));
//		
//		Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
//		
//		
//		return new PageResultDTO<>(result, fn);
//	}
	
	@Override
	public BoardDTO read(Long bno) {
		
		Object result = boardRepository.getBoardByBno(bno);
		
		Object[] arr = (Object[]) result;
		
		return entityToDTO((Board) arr[0], (Member) arr[1], (Long) arr[2]);
	}

	@Transactional
	@Override
	public void removeWithReplies(Long bno) {
		
//		replyRepository.deleteByBno(bno);
		boardRepository.deleteById(bno);
	}

	@Override
	public void modify(BoardDTO boardDTO) {
		
//		Board board = boardRepository.findById(boardDTO.getBno()).get();
//		
//		if(board != null) {
//			board.change(boardDTO.getTitle(), boardDTO.getContent());
//		}
		
		Board board = dtoToEntity(boardDTO);
		boardRepository.save(board);
	}
	
	



}






