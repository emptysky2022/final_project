package com.campers.camfp.repository.heart;

import com.campers.camfp.dto.heart.HeartDTO;
import com.campers.camfp.entity.heart.Heart;

public interface HeartQuerydsl {

	public Heart findHeart(HeartDTO dto);
}
