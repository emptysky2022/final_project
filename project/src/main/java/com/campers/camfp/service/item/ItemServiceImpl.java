package com.campers.camfp.service.item;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.campers.camfp.dto.board.BoardDTO;
import com.campers.camfp.dto.item.ItemDTO;
import com.campers.camfp.dto.page.PageRequestDTO;
import com.campers.camfp.dto.page.PageResultDTO;
import com.campers.camfp.entity.board.Board;
import com.campers.camfp.entity.item.Item;
import com.campers.camfp.entity.member.Member;
import com.campers.camfp.repository.item.ItemRepository;

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

	@Override
	public PageResultDTO<ItemDTO, Object[]> getListOfPage(PageRequestDTO pageRequestDTO) {
		log.info("pageRequestDTO : " + pageRequestDTO);
		
		Page<Object[]> result = itemRepository.getListWithPage(pageRequestDTO.getPageable(Sort.by("ino").descending()));
		
		Function<Object[], ItemDTO> fn = (en -> 
				entityToDto((Item)en[0], (Member)en[1]));
		
		return new PageResultDTO<>(result, fn);
	}

}
