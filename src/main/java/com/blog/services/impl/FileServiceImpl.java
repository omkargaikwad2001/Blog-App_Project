package com.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
//		file name
		String name=file.getOriginalFilename();
		
//		random name generate file
		String randomID=UUID.randomUUID().toString();
		String fileName1=randomID.concat(name.substring(name.lastIndexOf(".")));
		
//		fullpath
		String filepath=path+File.separator+fileName1;
		
//		create folder if not created
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
//		file copy
		Files.copy(file.getInputStream(), Paths.get(filepath));
		
		return name;
	}

	@Override
	public InputStream getResourse(String path, String fileName) throws FileNotFoundException {
		
//		fullpath
		String fullpath=path+File.separator+fileName;
		
		InputStream is = new FileInputStream(fullpath);
		
		return is;
	}
	
	
	
}
