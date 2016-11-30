package com.duayres.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

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
        	byte[] bytes=file.getBytes();
        	ServletContext context = request.getServletContext();
            String filesPath = context.getRealPath("/")+"files/";
            String origName=file.getOriginalFilename();

            File serverFile = new File(filesPath+origName);
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
            
            return "success ";
        }
        catch(Exception e)
        {
            return "error = "+e;
        }

    }
}
