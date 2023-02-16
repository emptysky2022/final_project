package com.campers.camfp.service.item;

import java.util.List;

import com.campers.camfp.dto.item.ItemDTO;
import com.campers.camfp.dto.page.PageRequestDTO;
import com.campers.camfp.dto.page.PageResultDTO;
import com.campers.camfp.entity.item.Item;
import com.campers.camfp.entity.member.Member;

public interface ItemService {

	//상품 등록
	Long register(ItemDTO itemDTO);
	
	//상품 하나 가져오기
	ItemDTO getOne(Long ino);
	
	//상품 리스트 가져오기(전체)
	List<ItemDTO> getList();
	
	//상품 리스트 가져오기(페이징)
	PageResultDTO<ItemDTO, Object[]> getListOfPage(PageRequestDTO pageRequestDTO);
	
	//상품 수정하기
	void modify(ItemDTO itemDTO);
	
	//상품 삭제하기
	void remove(Long ino);
	
	//DTO를 entity로 변환
	default Item dtoToEntity(ItemDTO itemDTO) {
		Item item = Item.builder()
						.ino(itemDTO.getIno())
						.member(Member.builder().mno(itemDTO.getMno()).build())
						.name(itemDTO.getName())
						.thumbnail(itemDTO.getThumbnail())
						.brand(itemDTO.getBrand())
						.maker(itemDTO.getMaker())
						.category1(itemDTO.getCategory1())
						.category2(itemDTO.getCategory2())
						.price(itemDTO.getPrice())
						.link(itemDTO.getLink())
						.type(itemDTO.getType())
						.count(itemDTO.getCount())
						.star(itemDTO.getStar())
						.heart(itemDTO.getHeart())
						.build();
		
		return item;
	}
	
	default ItemDTO entityToDto(Item item, Member member) {
		ItemDTO itemDTO = ItemDTO.builder()
								 .ino(item.getIno())
								 .mno(member.getMno())
								 .nickname(member.getNickname())
								 .name(item.getName())
								 .thumbnail(item.getThumbnail())
								 .brand(item.getBrand())
								 .maker(item.getMaker())
								 .category1(item.getCategory1())
								 .category2(item.getCategory2())
								 .price(item.getPrice())
								 .link(item.getLink())
								 .type(item.getType())
								 .count(item.getCount())
								 .star(item.getStar())
								 .heart(item.getHeart())
								 .build();
		
		return itemDTO;
	}
	default ItemDTO entityToDto(Item item) {
		ItemDTO itemDTO = ItemDTO.builder()
				.ino(item.getIno())
				.mno(item.getMember().getMno())
				.nickname(item.getMember().getNickname())
				.name(item.getName())
				.thumbnail(item.getThumbnail())
				.brand(item.getBrand())
				.maker(item.getMaker())
				.category1(item.getCategory1())
				.category2(item.getCategory2())
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
