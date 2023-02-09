package com.campers.camfp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.campers.camfp.dto.ItemHistoryDTO;
import com.campers.camfp.entity.ItemHistory;
import com.campers.camfp.repository.ItemHistoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ItemHistoryServiceImpl implements ItemHistoryService{

	private final ItemHistoryRepository itemHistoryRepository;
	
	@Override
	public Long register(ItemHistoryDTO itemHistoryDTO) {
		ItemHistory itemHistory = dtoToEntity(itemHistoryDTO);
		itemHistoryRepository.save(itemHistory);
		log.info("register item history : " + itemHistoryDTO);
		return itemHistory.getIhno();
	}

	@Override
	public ItemHistoryDTO getOne(Long ihno) {
		 ItemHistory itemHistory = itemHistoryRepository.findById(ihno).get();
		 ItemHistoryDTO itemHistoryDTO = entityToDto(itemHistory);
		return itemHistoryDTO;
	}
	
	@Override
	public List<ItemHistoryDTO> getHistoryOfUser(String mid) {
		return null;
	}

	@Override
	public void modify(ItemHistoryDTO itemHistoryDTO) {
		log.info("modify item history : " + itemHistoryDTO);
		ItemHistory itemHistory = dtoToEntity(itemHistoryDTO);
		itemHistoryRepository.save(itemHistory);
	}

	@Override
	public void remove(Long ihno) {
		log.info("delete item history num : " + ihno);
		itemHistoryRepository.deleteById(ihno);
	}


}
