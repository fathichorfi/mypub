package com.app.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.service.FileUploadService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = RequestPath.FILE_UPLOAD)
public class FileUploadController {

	@Autowired
	FileUploadService fileUploadService;

	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file, @RequestParam Long id,
			@RequestParam String pathType) {
		String fileName = fileUploadService.storeFile(fileUploadService.convertMultipartFile2File(file), id, pathType);
		return ResponseEntity.ok(fileName);
	}

}
