package com.campers.camfp.service.item;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.campers.camfp.dto.item.ItemReviewDTO;
import com.campers.camfp.entity.item.Item;
import com.campers.camfp.entity.item.ItemReview;
import com.campers.camfp.repository.item.ItemRepository;
import com.campers.camfp.repository.item.ItemReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ItemReviewServiceImpl implements ItemReviewService{

	private final ItemReviewRepository itemReviewRepository;
	private final ItemRepository itemRepository;

	@Override
	public Long register(ItemReviewDTO itemReviewDTO) {
		log.info("register item review : " + itemReviewDTO);
		ItemReview itemReview = dtoToEntity(itemReviewDTO);
		itemReviewRepository.save(itemReview);
		Item item = itemRepository.findById(itemReviewDTO.getIno()).get();
		item.changeStar(itemReviewRepository.countStar(itemReviewDTO.getIno()));
		itemRepository.save(item);
		return itemReview.getIrno();
	}

	@Override
	public ItemReviewDTO getOne(Long irno) {
		log.info("get one item review : " + irno);
		ItemReview itemReview = itemReviewRepository.findById(irno).get();
		ItemReviewDTO itemReviewDTO = entityToDto(itemReview);
		return itemReviewDTO;
	}

	@Override
	public int heartOfMember(Long irno) {
		ItemReview itemReview = itemReviewRepository.findById(irno).get();
		itemReview.increseHeart();
		itemReviewRepository.save(itemReview);
		
		return itemReview.getHeart();
	}

	@Override
	public List<ItemReviewDTO> getReviewOfItem(Long ino) {
		log.info("get all list with one item : " + ino);
		List<ItemReview> Entities = itemReviewRepository.getReviewWithIno(ino);
		List<ItemReviewDTO> result = Entities.stream().map(Entity -> entityToDto(Entity)).collect(Collectors.toList());
		
		return result;
	}

	@Override
	public List<ItemReviewDTO> getReviewOfMember(String mid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long modify(ItemReviewDTO itemReviewDTO) {
		itemReviewDTO.setIno(itemReviewRepository.findById(itemReviewDTO.getIrno()).get().getItem().getIno());
		ItemReview itemReview = dtoToEntity(itemReviewDTO);
		itemReviewRepository.save(itemReview);
		Item item = itemRepository.findById(itemReview.getItem().getIno()).get();
		item.changeStar(itemReviewRepository.countStar(itemReviewDTO.getIno()));
		itemRepository.save(item);
		return item.getIno();
	}

	@Override
	public void remove(Long irno) {
		log.info("remove item review num : " + irno);
		Long ino = itemReviewRepository.findById(irno).get().getItem().getIno();
		Item item = itemRepository.findById(ino).get();
		item.changeStar(itemReviewRepository.countStar(ino));
		itemRepository.save(item);
		itemReviewRepository.deleteById(irno);
	}



}