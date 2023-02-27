package com.campers.camfp.service.heart;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.campers.camfp.dto.heart.HeartDTO;
import com.campers.camfp.entity.heart.Heart;
import com.campers.camfp.repository.heart.HeartListRepository;
import com.nimbusds.jose.Header;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService {

	public final HeartListRepository heartListRepository;

	public HeartDTO findHeart(HeartDTO dto) {
		
		if (heartListRepository.findHeart(dto) == null) {
			return null;
		}
		
		Heart value = heartListRepository.findHeart(dto);
		log.info("entity 데이터");
		log.info(value);
		HeartDTO data = entityToDto(value);
		log.info("dto 데이터");
		log.info(data);
		return data;
	}

	@Override
	public HeartDTO saveHeart(HeartDTO dto) {
		Heart heart = dtoToEntity(dto);
		dto = entityToDto(heartListRepository.save(heart));
		
		return dto;
	}

	@Override
	public HeartDTO removeHeart(HeartDTO dto) {
		Heart heart = dtoToEntity(dto);
		heartListRepository.deleteById(heart.getHlno());
		return null;
	}

}
