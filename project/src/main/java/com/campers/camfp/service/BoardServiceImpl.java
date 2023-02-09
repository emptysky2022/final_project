package com.campers.camfp.service;

import org.springframework.stereotype.Service;

import com.campers.camfp.dto.BoardDTO;
import com.campers.camfp.entity.Board;
import com.campers.camfp.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	public final BoardRepository boardRepository;
	
	@Override
	public Long register(BoardDTO boardDTO) {

		log.info("BoardDTO---------------");
		log.info(boardDTO);
		
		Board entityBoardDTO = dtoToEntity(boardDTO);
		log.info(entityBoardDTO);
		
		boardRepository.save(entityBoardDTO);
		
		return entityBoardDTO.getBno();
	}

}
