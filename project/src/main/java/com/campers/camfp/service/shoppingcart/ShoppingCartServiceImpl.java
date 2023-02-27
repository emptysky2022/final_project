package com.campers.camfp.service.shoppingcart;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.campers.camfp.dto.shopingcart.ShoppingCartDTO;
import com.campers.camfp.entity.item.Item;
import com.campers.camfp.entity.shoppingcart.ShoppingCart;
import com.campers.camfp.repository.item.ItemRepository;
import com.campers.camfp.repository.shoppingcart.ShoppingCartRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService{

	private final ShoppingCartRepository shoppingCartRepository;
	
	@Override
	public Long register(Long ino, Long mno) {
		ShoppingCartDTO shoppingCartDTO = ShoppingCartDTO.builder()
											.ino(ino)
											.mno(mno)
											.amount(1)
											.build();
											
		
		ShoppingCart shoppingCart = dtoToEntity(shoppingCartDTO);
		shoppingCartRepository.save(shoppingCart);
		return shoppingCart.getSno();
	}

	@Override
	public ShoppingCartDTO getOne(Long sno) {
		ShoppingCart shoppingCart = shoppingCartRepository.findById(sno).get();
		ShoppingCartDTO shoppingCartDTO = entityToDto(shoppingCart);
		return shoppingCartDTO;
	}

	@Override
	public List<ShoppingCartDTO> getCartOfMember(Long mno) {
		List<ShoppingCart> entities = shoppingCartRepository.findCartOfMember(mno).get();
		
		List<ShoppingCartDTO> result = entities.stream().map(en ->entityToDto(en)).collect(Collectors.toList());
		
		log.info(result);
		return result;
	}

	@Override
	public void modify(ShoppingCartDTO shoppingCartDTO) {
		log.info("modify shopping cart : " + shoppingCartDTO);
		ShoppingCart shoppingCart = dtoToEntity(shoppingCartDTO);
		shoppingCartRepository.save(shoppingCart);
	}

	@Override
	public void remove(Long sno) {
		log.info("remove shopping cart num : " + sno);
		shoppingCartRepository.deleteById(sno);
	}



}