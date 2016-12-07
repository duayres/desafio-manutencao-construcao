package com.duayres.service;

import java.io.File;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.commons.fileupload.InvalidFileNameException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {
	public String uploadFile(MultipartFile file, ServletContext context) throws Exception{
        String filesPath = context.getRealPath("/")+"WEB-INF/assets/uploads/";//System.getenv("HOME")
        String uniqueName=UUID.randomUUID().toString()+"_"+file.getOriginalFilename();

        File serverFile = new File(filesPath+uniqueName);
        System.out.println(filesPath+uniqueName);
        file.transferTo(serverFile);
        
        return uniqueName;
	}

	public String uploadImageFile(MultipartFile file, ServletContext context) throws Exception{
		String mimeType = context.getMimeType(file.getOriginalFilename());
		if (!mimeType.startsWith("image/")) {
		    throw new InvalidFileNameException("image", "file is not an image");
		}
		
        String filesPath = context.getRealPath("/")+"WEB-INF/assets/uploads/";//System.getenv("HOME")
        String uniqueName=UUID.randomUUID().toString()+"_"+file.getOriginalFilename();

        File serverFile = new File(filesPath+uniqueName);
        System.out.println(filesPath+uniqueName);
        file.transferTo(serverFile);
        
        return uniqueName;
	}
}
