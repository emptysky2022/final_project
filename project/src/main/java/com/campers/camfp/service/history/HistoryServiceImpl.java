package com.campers.camfp.service.history;

import java.util.List;

import org.springframework.stereotype.Service;

import com.campers.camfp.dto.history.HistoryDTO;
import com.campers.camfp.entity.history.History;
import com.campers.camfp.repository.history.HistoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService{

	private final HistoryRepository historyRepository;

	@Override
	public Long register(HistoryDTO historyDTO) {
		History history = dtoToEntity(historyDTO);
		historyRepository.save(history);
		log.info("register  history : " + historyDTO);
		return history.getHno();
	}

	@Override
	public HistoryDTO getOne(Long hno) {
		History history = historyRepository.findById(hno).get();
		HistoryDTO historyDTO = entityToDto(history);
		log.info("get one history : " + historyDTO);

		return historyDTO;
	}

	@Override
	public List<HistoryDTO> getHistoryOfUser(String mid) {
		return null;
	}

	@Override
	public void modify(HistoryDTO historyDTO) {
		log.info("modify  history : " + historyDTO);
		History history = dtoToEntity(historyDTO);
		historyRepository.save(history);
	}

	@Override
	public void remove(Long hno) {
		log.info("delete  history num : " + hno);
		historyRepository.deleteById(hno);
	}




}