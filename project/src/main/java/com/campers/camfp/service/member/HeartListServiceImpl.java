package com.campers.camfp.service.member;

import org.springframework.stereotype.Service;

import com.campers.camfp.repository.member.HeartListRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class HeartListServiceImpl implements HeartListService {
	
	public final HeartListRepository heartListRepository;

}
