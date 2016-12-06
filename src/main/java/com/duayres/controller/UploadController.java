package com.duayres.controller;

import java.io.File;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {
	@RequestMapping(value="/uploadImage",method = RequestMethod.POST)
    public @ResponseBody String uploadImage(@RequestParam("image") MultipartFile file,HttpServletRequest request)
    {
        try
        {
        	//byte[] bytes=file.getBytes();
        	ServletContext context = request.getServletContext();
            String filesPath = context.getRealPath("/")+"WEB-INF/assets/uploads/";//System.getenv("HOME")
            String origName=UUID.randomUUID().toString()+"_"+file.getOriginalFilename();

            File serverFile = new File(filesPath+origName);
            System.out.println(filesPath+origName);
            /*BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();*/
            file.transferTo(serverFile);
            
            return "{\"uniqueFile\": \""+origName+"\" }";
        }
        catch(Exception e)
        {
            return "{\"uniqueFile\": \"error\"}";//+e;
        }

    }
}
