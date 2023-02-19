package com.campers.camfp.service.board;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.campers.camfp.dto.board.ReplyDTO;
import com.campers.camfp.entity.board.Board;
import com.campers.camfp.entity.board.Reply;
import com.campers.camfp.repository.board.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{
	
	private final ReplyRepository replyRepository;
	
	@Override
	public List<ReplyDTO> getListOfBoard(Long bno) {
		
		// bno -> board
		Board board = Board.builder().bno(bno).build(); 
		
		List<Reply> result = replyRepository.findByBoard(board);
		
		return result.stream().map(boardReply -> entityToDTO(boardReply)).collect(Collectors.toList());
	}

	@Override
	public Long replyRegister(ReplyDTO replyDTO) {
		
		Reply reply = dtoToEntity(replyDTO);
		replyRepository.save(reply);
		
		return reply.getRno();
	}

	@Override
	public void modify(ReplyDTO replyDTO) {
		
		// 
		Optional<Reply> result = replyRepository.findById(replyDTO.getRno()); 
		
		if(result.isPresent()) {// Optional 객체가 값을 가지고 있으면 실행 / 값이 없으면 넘어감
			Reply reply = result.get();
				  reply.changeContent(replyDTO.getContent()); // 일단 예제보고 따라한건데 change를 왜 따로 만들어서 쓴걸까여....
				  reply.changeHeart(replyDTO.getHeart());
				  
			replyRepository.save(reply);
		}
	}

	@Override
	public void remove(Long rno) {
		replyRepository.deleteById(rno);
		
	}

	
	
}
