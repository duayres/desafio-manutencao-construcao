package com.duayres.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duayres.model.Localizacao;
import com.duayres.repository.ILocalizacaoRepository;

@Controller
@RestController
@RequestMapping("/localizacao")
public class LocalizacaoController {

	@Autowired
	ILocalizacaoRepository localizacaoRepository;
	
	@RequestMapping("/busca/{termo}")
	public List<Localizacao> findByNome(@PathVariable("termo") String termo){
		return localizacaoRepository.findByNomeContainingIgnoreCase(termo);
	}
	
}
