package com.campers.camfp.service.heart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.campers.camfp.config.type.ProductType;
import com.campers.camfp.dto.heart.HeartDTO;
import com.campers.camfp.entity.heart.Heart;
import com.campers.camfp.repository.board.BoardRepository;
import com.campers.camfp.repository.camp.CampRepository;
import com.campers.camfp.repository.heart.HeartListRepository;
import com.campers.camfp.repository.item.ItemRepository;
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

	@Override
	public List<HeartDTO> getListOfProductType(Long mno, ProductType productType) {
		log.info("여기 좋아요 목록 데리러 왔어요");
		
		List<Heart> heartEntities = heartListRepository.getHeartByProductType(mno, productType);
		log.info("하트 목록은 뿌려주나요?");
		log.info(heartEntities);
		List<HeartDTO> result = heartEntities.stream().map(entity -> entityToDto(entity)).collect(Collectors.toList());
		
		log.info("결과 뿌려주러 왔어요");
		log.info(result);
		
		return result;
	}

}
