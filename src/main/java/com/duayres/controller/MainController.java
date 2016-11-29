package com.duayres.controller;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.duayres.model.Agendamento;
import com.duayres.model.TipoDeEquipamento;
import com.duayres.model.Usuario;
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
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(){
		if (usuarioService.listAll().isEmpty()){
				Usuario u = new Usuario();
				u.setTipoUsuario(com.duayres.model.TipoUsuario.ADMINISTRADOR);
				u.setEmail("eduardo@eduardoayres.com");
				u.setNome("Eduardo Cebola");
				u.setStatus(true);
				/*u.setSenha("282ea255eb874bb63fb22f2f4e54a0a1f1e746e6");
				u.setConfSenha("282ea255eb874bb63fb22f2f4e54a0a1f1e746e6");*/
				
				u.setSenha("$2a$10$2Ew.Cha8uI6sat5ywCnA0elRRahr91v4amVoNV5G9nQwMCpI3jhvO");
				u.setConfSenha("$2a$10$2Ew.Cha8uI6sat5ywCnA0elRRahr91v4amVoNV5G9nQwMCpI3jhvO");
				u=this.usuarioService.save(u);
			}
		
		return new ModelAndView("login");
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Usuario> doLogin(Usuario usuario){
		System.out.println("existo!");
		try {
			Usuario user = usuarioService.login(usuario);
			return ResponseEntity.ok(user);
		} catch (UsernameNotFoundException e) {
			Usuario user = new Usuario();
			user.setException("Usuario e/ou senha incorretos");
			return ResponseEntity.badRequest().body(user);
		}
	}
	
	@RequestMapping(value = "/currentUser", method = RequestMethod.GET)
	public ResponseEntity<Usuario> currentUser	( @AuthenticationPrincipal User user ){
		Optional<Usuario> usuario=usuarioService.findByEmailIgnoreCaseAndStatusTrue(user.getUsername());
		usuario.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
		usuario.get().setSenha("");
		return ResponseEntity.ok(usuario.get());
	}

	
	@RequestMapping("/home")
	public String home(){
		
		if (usuarioService.listAll().isEmpty()){
			Usuario u = new Usuario();
			u.setTipoUsuario(com.duayres.model.TipoUsuario.ADMINISTRADOR);
			u.setEmail("cebola@cebola.com");
			u.setNome("Eduardo Cebola");
			u.setStatus(true);
			u.setSenha("282ea255eb874bb63fb22f2f4e54a0a1f1e746e6");
			u.setConfSenha("282ea255eb874bb63fb22f2f4e54a0a1f1e746e6");
			u=this.usuarioService.save(u);
			
			
			TipoDeEquipamento e = new TipoDeEquipamento();
			e.setDescricao("Ar condicionado da minie");
			e.setNome("Ar condicionado CCE");
			e.setStatus(true);
			//e.setFoto(null);
			e=this.tpService.save(e);
			
			Agendamento a = new Agendamento();
			a.setDataInicial(Calendar.getInstance());
			a.setDataFinal(Calendar.getInstance());
			a.setMembros(Arrays.asList(u));
			a.setTipoEquipamento(e);
			//a.setLocalizacao(new Localizacao());
			
			this.agService.save(a);
		}
		
		return "index";
	}
	
}
