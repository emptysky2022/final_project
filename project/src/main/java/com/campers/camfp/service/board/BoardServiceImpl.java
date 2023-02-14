package com.campers.camfp.service.board;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.campers.camfp.dto.board.BoardDTO;
import com.campers.camfp.dto.page.PageRequestDTO;
import com.campers.camfp.dto.page.PageResultDTO;
import com.campers.camfp.entity.board.Board;
import com.campers.camfp.repository.board.BoardRepository;

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

	@Override
	public BoardDTO read(Long bno) {
		
		Optional<Board> result = boardRepository.findById(bno);
		
		return result.isPresent() ? entityToDTO(result.get()) : null;
	}

	@Override
	public PageResultDTO<BoardDTO, Board> getList(PageRequestDTO pageRequestDTO) {
		
		Pageable pageable = pageRequestDTO.getPageable(Sort.by("bno").descending());

		log.info("pageRequestDTO : " + pageRequestDTO);
		
		Page<Board> result = boardRepository.findAll(pageable);
		
		Function<Board, BoardDTO> fn = (en -> entityToDTO(en));
		
		return new PageResultDTO<>(result, fn);
	}

	@Override
	public void removeWithReplies(Long bno) {
		// TODO Auto-generated method stub
		
	}


}






