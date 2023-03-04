package com.campers.camfp.service.camp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.campers.camfp.config.type.TableType;
import com.campers.camfp.dto.board.BoardDTO;
import com.campers.camfp.dto.camp.CampDTO;
import com.campers.camfp.dto.page.PageRequestDTO;
import com.campers.camfp.dto.page.PageResultDTO;
import com.campers.camfp.entity.board.Board;
import com.campers.camfp.entity.camp.Camp;
import com.campers.camfp.entity.camp.CampCalender;
import com.campers.camfp.entity.camp.CampReview;
import com.campers.camfp.entity.member.Member;
import com.campers.camfp.repository.camp.CampCalenderRepository;
import com.campers.camfp.repository.camp.CampRepository;
import com.campers.camfp.repository.camp.CampReviewRepository;

import antlr.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@SuppressWarnings("unchecked")
public class CampServiceImpl implements CampService {

	// 파일 업로드 경로를 설정합니다.
	@Value("${upload.path}")
	private String uploadPath;

	private final CampRepository campRepository;
	private final CampReviewRepository campReviewRepository;
	private final CampCalenderRepository campCalenderRepository;

	@Autowired
	public CampServiceImpl(CampRepository camp, CampReviewRepository review, CampCalenderRepository calender) {
		this.campRepository = camp;
		this.campReviewRepository = review;
		this.campCalenderRepository = calender;
	}

	@Override
	public Object findbyId(TableType table, Long num) {

		Object value = null;
		switch (table) {

		case CAMP:
			Camp camp = campRepository.findById(num).get();
			value = camp;
			break;

		case CAMPREVIEW:
			CampReview campreview = campReviewRepository.findById(num).get();
			value = campreview;
			break;

		case CAMPCALENDER:
			CampCalender campCelender = campCalenderRepository.findById(num).get();
			value = campCelender;
			break;

		default:

			System.out.println("Not Found Type : " + table);

			break;
		}

		value = EntityToDTO(table, value);

		return value;
	}

	@Override
	public List<Object> findAll(TableType table, Long cno) {

		List<Object> result = new ArrayList<>();
		List<Object> buffer = new ArrayList<>();

		switch (table) {

		case CAMP:
			campRepository.findAll().forEach(data -> {
				buffer.add(data);
			});
			break;

		case CAMPREVIEW:
			campReviewRepository.findAll().forEach(data -> {
				buffer.add(data);
			});
			break;

		case CAMPCALENDER:
			campCalenderRepository.findAll().forEach(data -> {
				buffer.add(data);
			});
			break;

		default:

			System.out.println("Not Found Type : " + table);

			break;
		}

		buffer.forEach(value -> {
			result.add(EntityToDTO(table, value));
		});

		return result;

	}

	public List<Object> findHeartOrCountRank(TableType table, int num, String findType) {

		List<Object> bufferList = new ArrayList<>();
		List<Object> resultList = new ArrayList<>();

		switch (table) {

		case CAMP:
			List<Camp> camplist = (List<Camp>) campRepository.findHeartOrCountRank(table, num, findType);
			camplist.forEach(camp -> bufferList.add(camp));
			break;

		case CAMPREVIEW:
			List<CampReview> reivewlist = (List<CampReview>) campRepository.findHeartOrCountRank(table, num, findType);
			reivewlist.forEach(review -> bufferList.add(review));
			break;

		default:

			System.out.println("Not Found Type : " + table);

			break;
		}

		if (bufferList.size() > 0) {
			bufferList.forEach(data -> {
				resultList.add(EntityToDTO(table, data));
			});
		} else {
			log.info("데이터가 존재하지 않습니다.");
		}

		return resultList;

	}

	@Override
	public void remove(TableType table, Long num) {

		switch (table) {

		case CAMP:
			campRepository.deleteById(num);
			break;

		case CAMPREVIEW:
			campReviewRepository.deleteById(num);
			break;

		case CAMPCALENDER:
			campCalenderRepository.deleteById(num);
			break;

		default:

			System.out.println("Not Found Type : " + table);

			break;
		}

	}

	@Override
	public void register(TableType table, Object dto) {

		Object result = null;

		switch (table) {

		case CAMP:
			Camp camp = (Camp) DTOToEntity(table, dto);
			System.out.println(camp);
			campRepository.save(camp);
			break;

		case CAMPREVIEW:
			CampReview campReview = (CampReview) DTOToEntity(table, dto);
			campReviewRepository.save(campReview);

			// 캠핑장 review 생성시 camp star 를 올려줘야함.
			campRepository.addData(TableType.CAMP, campReview.getCamp().getCno(), "star", campReview.getStar());

			break;

		case CAMPCALENDER:
			CampCalender campCalender = (CampCalender) DTOToEntity(table, dto);
			campCalenderRepository.save(campCalender);
			break;

		default:

			System.out.println("Not Found Type : " + table);

			break;
		}

	}

	@Override
	public Object modify(TableType table, Object dto) {

		Object result = null;

		switch (table) {

		case CAMP:
			Camp camp = (Camp) DTOToEntity(table, dto);
			result = campRepository.save(camp);
			break;

		case CAMPREVIEW:
			CampReview campReview = (CampReview) DTOToEntity(table, dto);
			result = campReviewRepository.save(campReview);
			break;

		case CAMPCALENDER:
			CampCalender campCalender = (CampCalender) DTOToEntity(table, dto);
			result = campCalenderRepository.save(campCalender);
			break;

		default:

			System.out.println("Not Found Type : " + table);

			break;
		}

		return result;

	}

	@Override
	public List<Object> findDataOfMember(TableType table, Long mno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> findDataOfCamp(TableType table, Long cno, String[] findData) {

		List<Object> result = new ArrayList<>();

		switch (table) {

		case CAMP:
			List<Camp> campList = (List<Camp>) campRepository.findDataOfCamp(table, cno, findData);
			campList.forEach(value -> {
				result.add(EntityToDTO(table, value));
			});
			break;

		case CAMPREVIEW:
			List<CampReview> reivewList = (List<CampReview>) campRepository.findDataOfCamp(table, cno, findData);
			reivewList.forEach(value -> {
				result.add(EntityToDTO(table, value));
			});
			break;

		case CAMPCALENDER:
			List<CampCalender> calenderList = (List<CampCalender>) campRepository.findDataOfCamp(table, cno, findData);
			calenderList.forEach(value -> {
				result.add(EntityToDTO(table, value));
			});
			break;

		default:
			System.out.println("Not Found Type : " + table);
			break;

		}

		return result;
	}

	@Override
	public void addData(TableType table, Long cno, String findData, int num) {

		// 조회수니까 1 임
		campRepository.addData(table, cno, findData, num);

	}

	@Override
	public PageResultDTO<CampDTO, Object[]> findManayDataOfCamp(PageRequestDTO pageRequestDTO, String[] findDatas,
			String[] findLocations) {

		Pageable pageable = pageRequestDTO.getPageable(Sort.by("cno").descending());

		// 아무데도
		Function<Object[], CampDTO> fn = (en -> {
			// en 이 1개의 컬럼이라 기존에사용하더 entity dto 사용 못해서 새로 만들어줘야함 씨발 좆같은거.
			return EntityToDTO((Long) en[0], (Long) en[1], (String) en[2], (String) en[3], (String) (String) en[4],
					(String) en[5], (String) en[6], (String) en[7], (Integer) en[8], (Integer)en[9], (Integer)en[10], (Integer)en[11]);

		});

		Page<Object[]> result = campRepository.findManayDataOfCamp(findDatas, findLocations, pageable);

		log.info(fn);
		log.info(result);
		log.info(pageable);

		return new PageResultDTO<>(result, fn);
	}

	@Override
	public Double countStar(Long cno) {
		return campRepository.countStar(cno);
	}

	@Override
	public List<Object> findReservationDataofMember(String nickName) {
		
		List<Object> data = campRepository.findReservationDataofMember(nickName);
		log.info("여기까지 정상 ");
		return data;
	}
}
