package com.app.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
	
	public String storeFile(File file, Long id, String pathType);

	public File convertMultipartFile2File(MultipartFile multipartFile);
	
}
