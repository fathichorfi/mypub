package com.app.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.app.error.exception.FileStorageException;
import com.app.service.FileUploadService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {

	private Path fileStorageLocation;
	@Value("${app.file.upload.base-path}")
	private String basePath;

	public String storeFile(File file, Long id, String pathType) {
		this.fileStorageLocation = Paths.get(basePath + pathType).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			throw new FileStorageException("Could not create the directory when to store uploaded files", e);
		}
		String fileName = StringUtils.cleanPath(id + "-" + file.getName());
		try {
			// Copy file to the target path
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			InputStream targetStream = new FileInputStream(file);
			Files.copy(targetStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return fileName;
		} catch (Exception e) {
			throw new FileStorageException("Could not store file " + fileName + ", please try again!", e);
		}
	}

	public File convertMultipartFile2File(MultipartFile multipartFile) {
		// Create a temporary file
		File convFile = new File(multipartFile.getOriginalFilename());
		// Copy data from multi-part file to file
		try (FileOutputStream fos = new FileOutputStream(convFile); InputStream is = multipartFile.getInputStream()) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = is.read(buffer)) != -1) {
				fos.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			throw new FileStorageException("Could not convert the multi-part file to file: " + e.getMessage());
		}
		return convFile;
	}

}
