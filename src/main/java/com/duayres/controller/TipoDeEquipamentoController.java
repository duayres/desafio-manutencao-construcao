package com.duayres.controller;

import javax.servlet.http.HttpServletRequest;

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
	
	@RequestMapping(value="/uploadImage",method = RequestMethod.POST)
    public @ResponseBody String uploadImage(@RequestParam("file") MultipartFile file,HttpServletRequest request)
    {
		
		
    }
}
