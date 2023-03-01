package com.campers.camfp.util;

import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUploadUtil {
	public static boolean saveFile(String uploadPath, MultipartFile[] files) {

		boolean breturn = true;

		for (MultipartFile file : files) {
			// 파일 이름을 가져옵니다.
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			// UUID를 사용하여 파일 이름을 생성합니다.
			String uuid = UUID.randomUUID().toString();
			String saveName = uuid + "_" + fileName;

			try {
				// 파일을 저장할 경로와 이름을 설정합니다.
				File destFile = new File(uploadPath + File.separator + saveName);
				// 파일을 저장합니다.
				file.transferTo(destFile);

			} catch (IOException e) {
				e.printStackTrace();
				breturn = false;
			}
		}

		return breturn;

	}
}
