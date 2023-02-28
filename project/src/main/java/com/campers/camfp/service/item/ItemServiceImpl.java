package com.campers.camfp.service.item;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.campers.camfp.config.type.SearchType;
import com.campers.camfp.dto.board.BoardDTO;
import com.campers.camfp.dto.item.ItemDTO;
import com.campers.camfp.dto.page.PageRequestDTO;
import com.campers.camfp.dto.page.PageResultDTO;
import com.campers.camfp.entity.board.Board;
import com.campers.camfp.entity.item.Item;
import com.campers.camfp.entity.member.Member;
import com.campers.camfp.repository.history.HistoryRepository;
import com.campers.camfp.repository.item.ItemRepository;
import com.querydsl.core.util.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ItemServiceImpl implements ItemService{
	
	private final ItemRepository itemRepository;
	private final HistoryRepository historyRepository;

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
		item.increseCount();
		itemRepository.save(item);
		ItemDTO itemDTO = entityToDto(item);
		log.info("select one item " + itemDTO);
		return itemDTO;
	}

	//이후 로그인 정보 받아와서 Member 매개변수 넣을 예정
	//HeartListDTO의 필요성은 애매하고 HeartListRepository는 절실
	@Override
	public int heartOfMember(Long ino) {
		Item item = itemRepository.findById(ino).get();
		item.increseHeart();
		itemRepository.save(item);
		
		return item.getHeart();
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
	
	public Long modify(ItemDTO itemDTO) {
		Item item = dtoToEntity(itemDTO);
		log.info("modify item " + itemDTO);
		itemRepository.save(item);
		return itemDTO.getIno();
	}

	@Override
	public void remove(Long ino) {
		log.info("delete item " + ino);
		itemRepository.deleteById(ino);
	}

	@Override
	public PageResultDTO<ItemDTO, Object[]> getListOfPage(PageRequestDTO pageRequestDTO, List<String> condition) {
		log.info("pageRequestDTO : " + pageRequestDTO);
		Page<Object[]> result;
		if(condition == null || condition.isEmpty()) {
			result = itemRepository.getListWithPage(pageRequestDTO.getPageable(Sort.by("ino").descending()));			
		}else {
			Sort sort;
			if(StringUtils.isNullOrEmpty(condition.get(SearchType.TYPE.ordinal()))) {
				sort = Sort.by("ino").descending();
			}
			else {
				sort = Sort.by(condition.get(SearchType.TYPE.ordinal())).descending().and(Sort.by("ino").descending());
			}
			result = itemRepository.findBySearch(condition.get(SearchType.CATEGORY.ordinal()), condition.get(SearchType.KEYWORD.ordinal()), pageRequestDTO.getPageable(sort));
		}
		
		Function<Object[], ItemDTO> fn = (en -> 
		entityToDto((Item)en[0], (Member)en[1]));
		
		return new PageResultDTO<>(result, fn);
		
	}

}
