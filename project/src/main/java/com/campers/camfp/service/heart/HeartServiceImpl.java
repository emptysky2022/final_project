package com.campers.camfp.service.heart;

import org.springframework.stereotype.Service;

import com.campers.camfp.dto.heart.HeartDTO;
import com.campers.camfp.entity.heart.Heart;
import com.campers.camfp.repository.heart.HeartListRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService {
	
	public final HeartListRepository heartListRepository;

	/*
	 * public boolean findHeart(HeartDTO dto) { Heart heart = (Heart)
	 * heartListRepository.getHeart(dto.getMno(), dto.getProductNum());
	 * 
	 * dto = entityToDto(heart);
	 * 
	 * if (dto.equals("")) { return false; }
	 * 
	 * 
	 * return true; }
	 */
	
	@Override
	public Long saveHeart(HeartDTO dto) {
		Heart heart = dtoToEntity(dto);
		heartListRepository.save(heart);
		return null;
	}

	@Override
	public Long removeHeart(HeartDTO dto) {
		Heart heart = dtoToEntity(dto);
		heartListRepository.deleteById(heart.getHlno());
		return null;
	}


}
