package com.campers.camfp.service.shoppingcart;

import java.util.List;

import org.springframework.stereotype.Service;

import com.campers.camfp.dto.shopingcart.ShoppingCartDTO;
import com.campers.camfp.entity.shoppingcart.ShoppingCart;
import com.campers.camfp.repository.shoppingcart.ShoppingCartRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService{

	private final ShoppingCartRepository shoppingCartRepository;

	@Override
	public Long register(ShoppingCartDTO shoppingCartDTO) {
		log.info("register shopping cart : " + shoppingCartDTO);
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
	public List<ShoppingCartDTO> getCartOfMember(String mid) {
		// TODO Auto-generated method stub
		return null;
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