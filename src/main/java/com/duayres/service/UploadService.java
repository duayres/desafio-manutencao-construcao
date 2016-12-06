package com.duayres.service;

import java.io.File;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {
	public String uploadFile(MultipartFile file, ServletContext context, imageOnly = false) throws Exception{
    	//byte[] bytes=file.getBytes();
        String filesPath = context.getRealPath("/")+"WEB-INF/assets/uploads/";//System.getenv("HOME")
        String uniqueName=UUID.randomUUID().toString()+"_"+file.getOriginalFilename();

        File serverFile = new File(filesPath+uniqueName);
        System.out.println(filesPath+uniqueName);
        /*BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();*/
        file.transferTo(serverFile);
        
        return uniqueName;
	}
}
