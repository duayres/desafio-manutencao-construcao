package com.duayres.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.duayres.model.Agendamento;
import com.duayres.service.AgendamentoService;

@RestController
@RequestMapping("/api/agendamento/")
public class AgendamentoRestController {

	@Autowired
	AgendamentoService agendamentoService;
	
	@RequestMapping("list-all")
	public List<Agendamento> listAll(){
		return agendamentoService.listAll();
	}
	
	
	@RequestMapping(value="/activate/{id}", method=RequestMethod.GET)
	public String deactivateAgendamento(@PathVariable("id") Long idAgendamento){
		agendamentoService.exclude(idAgendamento);
		return "{\"code\": 200}";
	}
	
}
