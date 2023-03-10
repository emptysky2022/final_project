package com.campers.camfp.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.campers.camfp.dto.UploadResultDTO;
import com.mysema.commons.lang.URLEncoder;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class UploadController {
	//업로드시 이미지가 저장될 기본위치
	@Value("${com.campers.camfp.upload.path}")
	private String uploadPath;
	
	@Value("${com.campers.camfp.defaultImage}")
	private String defaultImage;
	
	@PostMapping("/uploadAjax")
	public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles, String webPath){
		//uploadFiles, webPath라는 이름으로 넘겨준 json타입 값이 매개변수로 들어옴
		List<UploadResultDTO> resultList = new ArrayList<>();
		//각각의 이미지를 반복문으로 처리
		for(MultipartFile uploadFile : uploadFiles) {
			//파일이 이미지 타입이 아닌 경우 반환
			if(uploadFile.getContentType().startsWith("image") == false) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			//파일이름 가져오기
			String originalName = uploadFile.getOriginalFilename();
			String fileName = originalName.substring(originalName.lastIndexOf("\\")+1);
			
			log.info("fileName : " + fileName);
			
			//폴더 만들기(+ register 모달 띄운 타입에 따라 폴더 분류)
			String folderPath = makeFolder(webPath);
			//랜덤값(파일 이름 중복 방지)
			String uuid = UUID.randomUUID().toString().substring(0, 7);
			//서버에 저장될 파일 이름
			String saveName = folderPath + File.separator + uuid + "_" + fileName;
			log.info("saveName" + saveName);
			//파일 경로 얻어오기
			Path savePath = Paths.get(saveName);
			
			try {
				//uploadFile(Ajax로 받아온 이미지)를 복사해서 붙여넣기
				uploadFile.transferTo(savePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//Ajax success에서 결과값으로 후처리
			resultList.add(new UploadResultDTO(fileName, uuid, folderPath));
		}
		return new ResponseEntity<>(resultList, HttpStatus.OK);
	}

	//register를 실행한 위치에 따라 Path 추가(camp, item, itemReview, etc...)
	private String makeFolder(String webPath) {
		File uploadFolder = new File(uploadPath, webPath);
		if(!uploadFolder.exists()) uploadFolder.mkdirs();
		
		log.info("uploadFolder path : " + uploadFolder.getPath());
		return uploadFolder.getPath();
	}
	
	//imageURL 저장해둔 곳에서 값을 가져와서 실제 이미지로 바꿔서 보여줌 folderType는 디렉토리 하위 폴더(itemreview, item, etc)
	@GetMapping("/display")
	public ResponseEntity<byte[]> getImage(String fileName, String folderType){
		ResponseEntity<byte[]> result = null;
			
		try {
			String srcFileName;
			File file;
			if(fileName.equals("null") || fileName.equals("")) {
				srcFileName = defaultImage;
				file = new File(uploadPath + File.separator + srcFileName);
			}else {
				srcFileName = URLDecoder.decode(fileName, "UTF-8");
				file = new File(uploadPath + File.separator + folderType + File.separator + srcFileName);
			}
			
			log.info("fileName : " + srcFileName);
			
			log.info(file);
			
			HttpHeaders header = new HttpHeaders();
			
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			
			//ResponseEntity에 파일 추가
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		}catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}
	
	@PostMapping("/removeFile")
	public ResponseEntity<Boolean> removeFile(String fileName){
		log.info("fileName");
		log.info(fileName);

		try {

			fileName = URLDecoder.decode(fileName, "UTF-8");
			File file = new File(fileName);
			boolean result = file.delete();
				
			return new ResponseEntity<Boolean>(result, HttpStatus.OK);
			
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
