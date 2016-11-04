package com.duayres.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class MainController {


	@RequestMapping(value="/teste")
	public void teste(){
		System.out.println("testão! xD");
	}	

}
