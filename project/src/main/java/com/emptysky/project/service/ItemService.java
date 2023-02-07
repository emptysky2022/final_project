package com.emptysky.project.service;

import java.util.List;

import com.emptysky.project.dto.ItemDTO;
import com.emptysky.project.entity.Item;

public interface ItemService {

	Long register(ItemDTO itemDTO);
	
	ItemDTO getOne(Long ino);
	
	List<ItemDTO> getList();
	
	void modify(ItemDTO itemDTO);
	
	void remove(Long ino);
	
	default Item dtoToEntity(ItemDTO itemDTO) {
		Item item = Item.builder()
						.ino(itemDTO.getIno())
						.name(itemDTO.getName())
						.image(itemDTO.getImage())
						.brand(itemDTO.getBrand())
						.maker(itemDTO.getMaker())
						.category1(itemDTO.getCategory1())
						.category2(itemDTO.getCategory2())
						.category3(itemDTO.getCategory3())
						.price(itemDTO.getPrice())
						.link(itemDTO.getLink())
						.type(itemDTO.getType())
						.count(itemDTO.getCount())
						.star(itemDTO.getStar())
						.heart(itemDTO.getHeart())
						.build();
		
		return item;
	}
	
	default ItemDTO entityToDto(Item item) {
		ItemDTO itemDTO = ItemDTO.builder()
								 .ino(item.getIno())
								 .name(item.getName())
								 .image(item.getImage())
								 .brand(item.getBrand())
								 .maker(item.getMaker())
								 .category1(item.getCategory1())
								 .category2(item.getCategory2())
								 .category3(item.getCategory3())
								 .price(item.getPrice())
								 .link(item.getLink())
								 .type(item.getType())
								 .count(item.getCount())
								 .star(item.getStar())
								 .heart(item.getHeart())
								 .build();
		
		return itemDTO;
	}
}
