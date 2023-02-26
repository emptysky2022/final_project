package com.campers.camfp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.campers.camfp.config.auth.PrincipalDetails;
import com.campers.camfp.dto.item.ItemDTO;
import com.campers.camfp.dto.shopingcart.ShoppingCartDTO;
import com.campers.camfp.service.item.ItemService;
import com.campers.camfp.service.shoppingcart.ShoppingCartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/cart")
public class ShoppingCartController {

	private final ShoppingCartService cartService;
	private final ItemService itemService;
	
	@PostMapping("/item/{ino}")
	public ResponseEntity<String> addCartOfItem(@PathVariable Long ino, @AuthenticationPrincipal PrincipalDetails principalDetails){
		log.info("cart 추가 controller 진입");
		if(principalDetails != null) {
			cartService.register(ino, principalDetails.getMember().getMno());
			
			return new ResponseEntity<>("add to cart success!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/items")
	public ResponseEntity<List<Object>> getCartOfMember(@AuthenticationPrincipal PrincipalDetails principalDetails){
		log.info("cart 아이템 controller 진입");
		if(principalDetails != null) {
			List<ShoppingCartDTO> cartItems = cartService.getCartOfMember(principalDetails.getMember().getMno());
			List<Object> result = new ArrayList<>();
			cartItems.forEach(item -> {
				result.add(List.of(itemService.getOne(item.getIno()), item));
			});
			
			log.info(result);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<ShoppingCartDTO>> getCartItems(@AuthenticationPrincipal PrincipalDetails principalDetails){
		if(principalDetails != null) {
			List<ShoppingCartDTO> result = cartService.getCartOfMember(principalDetails.getMember().getMno());
			
			log.info(result);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	
	@PutMapping("/items")
	public ResponseEntity<String> changeAmountOfItem(@RequestBody List<ShoppingCartDTO> cartList, @AuthenticationPrincipal PrincipalDetails principalDetails){
		log.info("여기 들와짐?");
		log.info(cartList);
		if(principalDetails != null) {
			cartList.forEach(cart -> {
				cartService.modify(cart);
			});
			
			return new ResponseEntity<>("modify to ItemAmount", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@DeleteMapping("/{sno}")
	public ResponseEntity<String> removeCartOfItem(@PathVariable Long sno){
		log.info("delete cart");
		cartService.remove(sno);
		return new ResponseEntity<>("remove success!", HttpStatus.OK);
	}
}
