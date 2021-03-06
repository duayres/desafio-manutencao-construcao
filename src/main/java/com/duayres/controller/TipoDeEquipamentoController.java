package com.duayres.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.InvalidFileNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.duayres.service.UploadService;

@Controller
public class TipoDeEquipamentoController {
	@Autowired
	UploadService uploadService;
	
	@RequestMapping(value="/uploadFoto",method = RequestMethod.POST)
    public @ResponseBody String uploadImage(@RequestParam("file") MultipartFile file,HttpServletRequest request)
    {
		try{
			return "{\"uniqueFile\": \""+uploadService.uploadImageFile(file, request.getServletContext())+"\"}";
		} catch (Exception e) {
			//return "{\"uniqueFile\": \"error\",\"msg\":\""+e.getMessage()+"\"}";
			return "{\"uniqueFile\": \"error\",\"msg\":\"A foto deve ser uma imagem válida\"}";
		}

    }
	
	@RequestMapping(value="/uploadManual",method = RequestMethod.POST)
    public @ResponseBody String uploadManual(@RequestParam("file") MultipartFile file,HttpServletRequest request)
    {
		try{
			return "{\"uniqueFile\": \""+uploadService.uploadFile(file, request.getServletContext())+"\"}";
		} catch (Exception e) {
			return "{\"uniqueFile\": \"error\"}";//+e;
		}

    }
}
