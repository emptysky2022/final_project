package com.campers.camfp.service.history;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.campers.camfp.config.type.HistoryType;
import com.campers.camfp.config.type.StateType;
import com.campers.camfp.dto.history.HistoryDTO;
import com.campers.camfp.entity.history.History;
import com.campers.camfp.entity.item.Item;
import com.campers.camfp.entity.shoppingcart.ShoppingCart;
import com.campers.camfp.repository.camp.CampCalenderRepository;
import com.campers.camfp.repository.camp.CampRepository;
import com.campers.camfp.repository.history.HistoryRepository;
import com.campers.camfp.repository.item.ItemRepository;
import com.campers.camfp.repository.shoppingcart.ShoppingCartRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService{

	private final HistoryRepository historyRepository;
	private final ItemRepository itemRepository;
	private final ShoppingCartRepository shoppingCartRepository;
	private final CampRepository campRepository;
	private final CampCalenderRepository campCalenderRepository;
	

	@Override
	public Long register(HistoryDTO historyDTO) {
		History history = dtoToEntity(historyDTO);
		historyRepository.save(history);
		log.info("register  history : " + historyDTO);
		return history.getHno();
	}

	@Override
	public void registerOfCart(List<Long> snos) {
		snos.forEach(sno -> {
			ShoppingCart cart = shoppingCartRepository.findById(sno).get();
			Item item = itemRepository.findById(cart.getIno()).get();
			History history = History.builder()
								.member(cart.getMember())
								.historyType(HistoryType.ITEM.toString())
								.historyNum(item.getIno())
								.name(item.getName())
								.state(StateType.PAY_END.toString())
								.price(item.getPrice())
								.amount(cart.getAmount())
								.build();
			historyRepository.save(history);
		});
	}
	
	@Override
	public HistoryDTO getOne(Long hno) {
		History history = historyRepository.findById(hno).get();
		HistoryDTO historyDTO = entityToDto(history);
		log.info("get one history : " + historyDTO);

		return historyDTO;
	}

	@Override
	public List<HistoryDTO> getHistoryOfMember(Long mno) {
		List<History> historyEntities = historyRepository.getHistoryOfMember(mno);
		
		log.info(historyEntities);
		
		List<HistoryDTO> result = historyEntities.stream()
				.map(entity -> entityToDto(entity)).collect(Collectors.toList());
		log.info("result = " + result);
		return result;
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