package com.duayres.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.duayres.model.Agendamento;
import com.duayres.service.AgendamentoService;
import com.duayres.service.MembroService;

@Controller
public class AgendamentoController {

	@Autowired
	AgendamentoService agendamentoService;
	@Autowired
	MembroService membroService;
	
	@RequestMapping(value="/delete-agendamento/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Agendamento> deleteAgendamento(@PathVariable("id") Long idAgendamento){
		Agendamento agendamento = agendamentoService.findByIdAgendamento(idAgendamento);
		if (agendamento == null) {
			return new ResponseEntity<Agendamento>(HttpStatus.NOT_FOUND);
		}
		if (agendamento.getDataFinal().after(Calendar.getInstance())){
			return new ResponseEntity<Agendamento>(HttpStatus.LOCKED);
		}
		membroService.deleteWithAgendamento(agendamento);
		agendamentoService.exclude(idAgendamento);
		return new ResponseEntity<Agendamento>(HttpStatus.NO_CONTENT);
	}
	
}
