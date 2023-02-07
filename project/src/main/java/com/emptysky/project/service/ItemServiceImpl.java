package com.emptysky.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.emptysky.project.dto.ItemDTO;
import com.emptysky.project.entity.Item;
import com.emptysky.project.repository.ItemRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ItemServiceImpl implements ItemService{
	
	private final ItemRepository itemRepository;

	@Override
	public Long register(ItemDTO itemDTO) {
		Item item = dtoToEntity(itemDTO);
		itemRepository.save(item);
		log.info("register item " + itemDTO);
		return item.getIno();
	}

	@Override
	public ItemDTO getOne(Long ino) {
		Item item = itemRepository.findById(ino).get();
		ItemDTO itemDTO = entityToDto(item);
		log.info("select one item " + itemDTO);
		return itemDTO;
	}

	@Override
	public List<ItemDTO> getList() {
		List<Item> resultEntity = itemRepository.findAll();
		List<ItemDTO> resultDTO = resultEntity
								  .stream()
								  .map(result -> entityToDto(result))
								  .collect(Collectors.toList());

		log.info("select all item " + resultDTO);
		return resultDTO;
	}

	@Override
	public void modify(ItemDTO itemDTO) {
		Item item = dtoToEntity(itemDTO);
		log.info("modify item " + itemDTO);
		itemRepository.save(item);
	}

	@Override
	public void remove(Long ino) {
		log.info("delete item " + ino);
		itemRepository.deleteById(ino);
	}
	
	
}
