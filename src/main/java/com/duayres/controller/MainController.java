package com.duayres.controller;

import java.util.Arrays;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.duayres.model.Agendamento;
import com.duayres.model.Localizacao;
import com.duayres.model.TipoDeEquipamento;
import com.duayres.model.Usuario;
import com.duayres.service.AgendamentoService;
import com.duayres.service.TipoDeEquipamentoService;
import com.duayres.service.UsuarioService;

@Controller
public class MainController {

	@Autowired
	private UsuarioService usuarioService;
	private AgendamentoService agService;
	private TipoDeEquipamentoService tpService;
	
	@RequestMapping("/teste")
	public String teste(){
		System.out.println("testão! xD");
		
		Usuario u = new Usuario();
		u.setTipo(com.duayres.model.TipoUsuario.ADMINISTRADOR);
		u.setEmail("cebola@cebola.com");
		u.setNomeUsuario("Eduardo Cebola");
		u.setStatus(true);
		u.setSenha("ahue1234");
		u.setConfSenha("ahue1234");
		this.usuarioService.inserir(u);
		
		
		TipoDeEquipamento e = new TipoDeEquipamento();
		e.setDescricao("Ar condicionado da minie");
		e.setNome("Ar condicionado CCE");
		e.setStatus(true);
		e.setFoto("www.troll.com/troll.jpg");
		tpService.inserir(e);
		
		/*Agendamento a = new Agendamento();
		a.setLocalizacao(new Localizacao());
		a.setDataInicial(Calendar.getInstance());
		a.setDataFinal(Calendar.getInstance());
		a.setMembros(Arrays.asList(u));
		
		agService.inserir(a);*/
		
		return "xD";
	}
}
