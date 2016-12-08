package com.duayres.desafio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.duayres.config.JPAConfigTest;
import com.duayres.config.SecurityConfig;
import com.duayres.config.ServiceConfig;
import com.duayres.config.WebConfig;
import com.duayres.model.Usuario;
import com.duayres.service.UsuarioService;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, JPAConfigTest.class, ServiceConfig.class, SecurityConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@WebAppConfiguration
@Transactional
@DbUnitConfiguration(databaseConnection = "dataSource")
public abstract class AbstractIntegrationTest {
	
	@Autowired(required = false)
	private UsuarioService usuarioService;
	
	protected UsernamePasswordAuthenticationToken authenticate(Long userId){
		Usuario user = this.usuarioService.findUserById(userId);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user,  null, getUserTipo(user));
		SecurityContextHolder.createEmptyContext();
		SecurityContextHolder.getContext().setAuthentication(token);
		return token;
	}
	
	private Collection<? extends GrantedAuthority> getUserTipo(Usuario user) {
		List<GrantedAuthority> permissoes = new ArrayList<GrantedAuthority>();
		permissoes.add(new SimpleGrantedAuthority("ROLE_"+user.getTipoUsuario()));
		return permissoes;
	}
}
