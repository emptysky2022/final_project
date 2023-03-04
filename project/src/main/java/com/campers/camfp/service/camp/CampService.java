package com.campers.camfp.service.camp;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.campers.camfp.config.type.TableType;
import com.campers.camfp.dto.camp.CampCalenderDTO;
import com.campers.camfp.dto.camp.CampDTO;
import com.campers.camfp.dto.camp.CampReviewDTO;
import com.campers.camfp.dto.page.PageRequestDTO;
import com.campers.camfp.dto.page.PageResultDTO;
import com.campers.camfp.entity.camp.Camp;
import com.campers.camfp.entity.camp.CampCalender;
import com.campers.camfp.entity.camp.CampReview;
import com.campers.camfp.entity.member.Member;

public interface CampService {

	public Object findbyId(TableType table, Long num);

	public void remove(TableType table, Long num);

	public Object modify(TableType table, Object dto);

	public void register(TableType table, Object dto);

	public List<Object> findAll(TableType table, Long cno);

	public List<Object> findHeartOrCountRank(TableType table, int num, String findType);

	public List<Object> findDataOfMember(TableType table, Long mno);

	public List<Object> findReservationDataofMember(String nickName);

	public List<Object> findDataOfCamp(TableType table, Long cno, String[] findData);

	public void addData(TableType table, Long cno, String findData, int num);

	public PageResultDTO<CampDTO, Object[]> findManayDataOfCamp(PageRequestDTO pageRequestDTO, String[] findDatas,
			String[] findLocations);

	public Double countStar(Long cno);

	/**
	 * @param table 변환할 table 명 table <br>
	 * @param dto   변환할 table 의 DataAccessObject <br>
	 * @return 해당 table Entity 를 Object 형식으로 Up casting 리턴
	 */
	default Object DTOToEntity(TableType table, Object dto) {
		Object value = null;

		switch (table) {

		case CAMP:

			CampDTO campDTO = (CampDTO) dto;
			System.out.println(campDTO);

			Camp camp = Camp.builder().cno(campDTO.getCno()).member(Member.builder().mno(campDTO.getMno()).build())
					.name(campDTO.getName()).thumbnail(campDTO.getThumbnail()).location(campDTO.getLocation())
					.camptype(campDTO.getCamptype()).address(campDTO.getAddress())
					.campintroduce(campDTO.getCampintroduce()).unit(campDTO.getUnit()).count(campDTO.getCount())
					.star(campDTO.getStar()).heart(campDTO.getHeart()).build();
			value = camp;
			break;

		case CAMPREVIEW:

			CampReviewDTO reviewDTO = (CampReviewDTO) dto;
			CampReview campReview = CampReview.builder().crno(reviewDTO.getCrno())
					.camp(Camp.builder().cno(reviewDTO.getCno()).build()).capture(reviewDTO.getCapture())
					.content(reviewDTO.getContent()).reviewer(reviewDTO.getReviewer()).star(reviewDTO.getStar())
					.heart(reviewDTO.getHeart()).build();

			value = campReview;
			break;

		case CAMPCALENDER:

			CampCalenderDTO calenderDTO = (CampCalenderDTO) dto;
			CampCalender campCalender = CampCalender.builder().ccno(calenderDTO.getCcno())
					.camp(Camp.builder().cno(calenderDTO.getCno()).build()).startdate(calenderDTO.getStartdate())
					.reservationer(calenderDTO.getReservationer()).enddate(calenderDTO.getEnddate()).build();
			value = campCalender;
			break;

		}

		return value;
	}

	/**
	 * @param table 변환할 table 명 table <br>
	 * @param dto   변환할 table 의 DataAccessObject <br>
	 * @retrun 해당 table DTO를 Object 형식으로 Up casting 리턴
	 * 
	 */
	default Object EntityToDTO(TableType table, Object entity) {
		Object value = null;
		switch (table) {

		case CAMP:

			Camp camp = (Camp) entity;
			CampDTO campDTO = CampDTO.builder().cno(camp.getCno()).mno(camp.getMember().getMno()).name(camp.getName())
					.thumbnail(camp.getThumbnail()).address(camp.getAddress()).unit(camp.getUnit())
					.location(camp.getLocation()).camptype(camp.getCamptype()).campintroduce(camp.getCampintroduce())
					.count(camp.getCount()).star(camp.getStar()).heart(camp.getHeart()).build();

			value = campDTO;
			break;

		case CAMPREVIEW:

			CampReview review = (CampReview) entity;
			CampReviewDTO campReview = CampReviewDTO.builder().crno(review.getCrno()).cno(review.getCamp().getCno())
					.capture(review.getCapture()).content(review.getContent()).reviewer(review.getReviewer())
					.star(review.getStar()).heart(review.getHeart()).build();

			value = campReview;
			break;

		case CAMPCALENDER:
			CampCalender calender = (CampCalender) entity;
			CampCalenderDTO campCalender = CampCalenderDTO.builder().ccno(calender.getCcno())
					.cno(calender.getCamp().getCno()).startdate(calender.getStartdate()).enddate(calender.getEnddate())
					.reservationer(calender.getReservationer()).build();
			value = campCalender;
			break;

		}

		return value;
	}

	default CampDTO EntityToDTO(Long cno, Long mno, String name, String thumbnail, String address, String location,
			String camptype, String campintroduce, Integer unit, Integer count, Integer star, Integer heart) {
		
		CampDTO value = CampDTO.builder().cno(cno).mno(mno).name(name).thumbnail(thumbnail).address(address).location(location).camptype(camptype)
				.campintroduce(campintroduce).unit(unit.intValue()).count(count.intValue()).star(star.intValue()).heart(heart.intValue()).build();
		return value;
	}

}
