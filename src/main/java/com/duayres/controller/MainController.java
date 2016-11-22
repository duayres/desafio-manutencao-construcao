package com.duayres.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.duayres.service.AgendamentoService;
import com.duayres.service.TipoDeEquipamentoService;
import com.duayres.service.UsuarioService;

@Controller
public class MainController {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private AgendamentoService agService;
	@Autowired
	private TipoDeEquipamentoService tpService;
	
	
	@RequestMapping("/login")
	public ModelAndView login(){
	return new ModelAndView("index");
	}
	
	@RequestMapping("/home")
	public ModelAndView home(){
	return new ModelAndView("index");
	}
	
	@RequestMapping("/")
	public ModelAndView teste(){
		System.out.println("testão! xD");
		
		/*Usuario u = new Usuario();
		u.setTipo(com.duayres.model.TipoUsuario.ADMINISTRADOR);
		u.setEmail("cebola@cebola.com");
		u.setNomeUsuario("Eduardo Cebola");
		u.setStatus(true);
		u.setSenha("ahue1234");
		u.setConfSenha("ahue1234");
		this.usuarioService.save(u);
		
		
		TipoDeEquipamento e = new TipoDeEquipamento();
		e.setDescricao("Ar condicionado da minie");
		e.setNome("Ar condicionado CCE");
		e.setStatus(true);
		e.setFoto("www.troll.com/troll.jpg");
		this.tpService.save(e);
		
		Agendamento a = new Agendamento();
		//a.setLocalizacao(new Localizacao());
		a.setDataInicial(Calendar.getInstance());
		a.setDataFinal(Calendar.getInstance());
		a.setMembros(Arrays.asList(u));
		
		this.agService.save(a);*/
		
		return new ModelAndView("login");
	}
}
