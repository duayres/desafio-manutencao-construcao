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
import com.duayres.model.Localizacao;
import com.duayres.model.Membro;
import com.duayres.model.TipoDeEquipamento;
import com.duayres.model.Usuario;
import com.duayres.repository.ILocalizacaoRepository;
import com.duayres.repository.IMembroRepository;
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
	@Autowired
	private IMembroRepository mRepo;
	@Autowired
	private ILocalizacaoRepository lRepo;

	
	@RequestMapping("/")
	public String toHome(){
		return "redirect:/home";
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		if (usuarioService.listAll().isEmpty()) {
			Usuario u = new Usuario();
			u.setTipoUsuario(com.duayres.model.TipoUsuario.ADMINISTRADOR);
			u.setEmail("eduardo@eduardoayres.com");
			u.setNome("Eduardo Ayres");
			u.setStatus(true);
			u.setSenha("$2a$10$2Ew.Cha8uI6sat5ywCnA0elRRahr91v4amVoNV5G9nQwMCpI3jhvO");
			u.setConfSenha("$2a$10$2Ew.Cha8uI6sat5ywCnA0elRRahr91v4amVoNV5G9nQwMCpI3jhvO");
			u = this.usuarioService.save(u);

			/// USUARIO 2
			Usuario u2 = new Usuario();
			u2.setTipoUsuario(com.duayres.model.TipoUsuario.COLABORADOR);
			u2.setEmail("teste@teste.com");
			u2.setNome("User Teste");
			u2.setStatus(true);
			u2.setSenha("$2a$10$2Ew.Cha8uI6sat5ywCnA0elRRahr91v4amVoNV5G9nQwMCpI3jhvO");
			u2.setConfSenha("$2a$10$2Ew.Cha8uI6sat5ywCnA0elRRahr91v4amVoNV5G9nQwMCpI3jhvO");
			u2 = this.usuarioService.save(u2);
			////

			TipoDeEquipamento e = new TipoDeEquipamento();
			e.setDescricao("Ar condicionado da minie");
			e.setNome("Ar condicionado CCE");
			e.setStatus(true);
			// e.setFoto(null);
			e = this.tpService.save(e);

			Agendamento a = new Agendamento();

			Calendar di=Calendar.getInstance();
			Calendar df=Calendar.getInstance();
			di.setTimeInMillis(1480676400000L);//2016-12-02 09:00:00
			df.setTimeInMillis(1480680000000L);//2016-12-02 10:00:00
			a.setDataInicial(di);
			a.setDataFinal(df);
			a.setTipoDeEquipamento(e);
			
			Localizacao loc = new Localizacao("assis");
			lRepo.saveAndFlush(loc);
			a.setLocalizacao(loc);

			Membro m = new Membro();
			m.setUsuario(u);
			m.setAgendamento(a);
			Membro m2 = new Membro();
			m2.setAgendamento(a);
			m2.setUsuario(u2);
			//a.setMembros(Arrays.asList(m,m2));
			
			/*Membro m = new Membro();
			m.setUsuario(u);
			m.setAgendamento(a);
			Membro m2 = new Membro();
			m2.setAgendamento(a);
			m2.setUsuario(u2);
			
			a.getMembros().add(m);
			a.getMembros().add(m2);*/
			
			a = agService.save(a);


			mRepo.save(m);
			mRepo.save(m2);

		}

		return new ModelAndView("login");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Usuario> doLogin(Usuario usuario) {
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
	public ResponseEntity<Usuario> currentUser(@AuthenticationPrincipal User user) {
		Optional<Usuario> usuario = usuarioService.findByEmailIgnoreCaseAndStatusTrue(user.getUsername());
		usuario.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
		usuario.get().setSenha("");
		return ResponseEntity.ok(usuario.get());
	}

	@RequestMapping("/home")
	public String home() {

		if (usuarioService.listAll().isEmpty()) {
			Usuario u = new Usuario();
			u.setTipoUsuario(com.duayres.model.TipoUsuario.ADMINISTRADOR);
			u.setEmail("cebola@cebola.com");
			u.setNome("Eduardo Cebola");
			u.setStatus(true);
			u.setSenha("282ea255eb874bb63fb22f2f4e54a0a1f1e746e6");
			u.setConfSenha("282ea255eb874bb63fb22f2f4e54a0a1f1e746e6");
			u = this.usuarioService.save(u);

			TipoDeEquipamento e = new TipoDeEquipamento();
			e.setDescricao("Ar condicionado da minie");
			e.setNome("Ar condicionado CCE");
			e.setStatus(true);
			// e.setFoto(null);
			e = this.tpService.save(e);

			Agendamento a = new Agendamento();
			a.setDataInicial(Calendar.getInstance());
			a.setDataFinal(Calendar.getInstance());
			// a.setMembros(Arrays.asList(u));
			a.setTipoDeEquipamento(e);
			// a.setLocalizacao(new Localizacao());

			this.agService.save(a);
		}

		return "index";
	}

}
