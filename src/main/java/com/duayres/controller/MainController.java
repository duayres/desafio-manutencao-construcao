package com.duayres.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class MainController {


	@RequestMapping(value="/")
	public void teste(){
		System.out.println("testão! xD");
	}	
	
}
