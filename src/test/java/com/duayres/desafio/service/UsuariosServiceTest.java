package com.duayres.desafio.service;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.security.core.userdetails.UsernameNotFoundException;*/

/*import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;*/
import com.duayres.desafio.AbstractIntegrationTest;
import com.duayres.model.TipoUsuario;
import com.duayres.model.Usuario;
import com.duayres.repository.IUsuarioRepository;
import com.duayres.service.UsuarioService;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
/*import com.duayres.service.exception.EmailJaCadastradoException;*/
import com.github.springtestdbunit.annotation.DatabaseTearDown;

public class UsuariosServiceTest extends AbstractIntegrationTest{
	public static final String DATASET_CENARIO_LIMPO = "classpath:datasets/AbstractDataset.xml";
	public static final String DATASET_USUARIOS = "classpath:datasets/UsuariosDataset.xml";
	
	@Autowired(required = false)
	private UsuarioService usuarioService;
	
	@Autowired(required = false)
	private IUsuarioRepository usuarioRepository;
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = {DATASET_USUARIOS}, connection = "dataSource")
	@DatabaseTearDown(DATASET_CENARIO_LIMPO)
	public void testListAllUsuariosMustPass(){
		/*this.authenticate(1);*/
		List<Usuario> usuarios = usuarioService.listAll();
		
		Assert.assertNotNull(usuarios);
		Assert.assertEquals(usuarios.size(), 2);
	}
	
	/**
	 * Metodo que testa com sucesso a inserção de um usuario
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = {DATASET_USUARIOS}, connection = "dataSource")
	@DatabaseTearDown(DATASET_CENARIO_LIMPO)
	public void testCadastrarUsuariosMustPass(){
		/*this.authenticate(1);*/
		Usuario user = new Usuario();
		user.setIdUsuario(null);
		user.setNomeUsuario("Maria Da Barbosa");
		user.setEmail("maria.db@gmail.com");
		user.setSenha("admin");
		user.setConfSenha("admin");
		user.setStatus(true);
		user.setTipo(TipoUsuario.COLABORADOR);
		
		user = usuarioService.save(user);
		
		Assert.assertNotNull(user.getIdUsuario());
		List<Usuario> usuarios = usuarioService.listAll();
		Assert.assertNotNull(usuarios);
		Assert.assertEquals(usuarios.size(), 3);
		
	}
	
	/**
	 * Metodo que testa com falha a inserção de um usuario
	 * A inserção falha pois o teste tenta inserir um email que ja existe, o que não é permitido (uk)
	 */
	@Test/*(expected = EmailJaCadastradoException.class)*/
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = {DATASET_USUARIOS}, connection = "dataSource")
	@DatabaseTearDown(DATASET_CENARIO_LIMPO)
	public void testCadastrarUsuariosMustFail(){
		/*this.authenticate(1);*/
		Usuario user = new Usuario();
		user.setIdUsuario(null);
		user.setNomeUsuario("Marcel Micheletto");
		user.setEmail("eduardo@eduardoayres.com");
		user.setSenha("admin");
		user.setConfSenha("admin");
		user.setStatus(true);
		user.setTipo(TipoUsuario.COLABORADOR);
		
		user = usuarioService.save(user);
		
		Assert.fail();
	}
	
	/**
	 * Metodo que testa com sucesso a edição de um usuario
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = {DATASET_USUARIOS}, connection = "dataSource")
	@DatabaseTearDown(DATASET_CENARIO_LIMPO)
	public void testEditarUsuariosMustPass(){
		/*this.authenticate(1);*/
		Usuario user = this.usuarioRepository.findOne(1);
		
		Assert.assertEquals(user.getNomeUsuario(), "Victor Carvalho");
		
		user.setNomeUsuario("Usuario alterado 1");
		user = usuarioService.save(user);
		
		Assert.assertEquals(user.getNomeUsuario(), "Usuario alterado 1");
	}
	
	/**
	 * Metodo que testa com falha a edição de um usuario
	 * A inserção falha pois o valor nomeUsuario para inserção é nulo
	 */
	@Test(expected = ConstraintViolationException.class)
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = {DATASET_USUARIOS}, connection = "dataSource")
	@DatabaseTearDown(DATASET_CENARIO_LIMPO)
	public void testEditarUsuariosMustFail(){
		/*this.authenticate(1);*/
		
		Usuario user = this.usuarioRepository.findOne(1);
		Assert.assertEquals(user.getNomeUsuario(), "Victor Carvalho");
		
		user.setNomeUsuario(null);
		user = usuarioService.save(user);
		
		Assert.fail();
	}
	
	/**
	 * Metodo que testa com sucesso o login do usuario
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = {DATASET_USUARIOS}, connection = "dataSource")
	@DatabaseTearDown(DATASET_CENARIO_LIMPO)
	public void testLoginMustPass(){
		Usuario usuario = new Usuario();
		usuario.setEmail("victor.blq@gmail.com");
		usuario.setSenha("admin");
		
		/*usuario = usuarioService.login(usuario);*/
		Assert.assertEquals(usuario.getEmail(), "victor.blq@gmail.com");
		Assert.assertEquals(usuario.getSenha(), "522d7ee4d28e5ab11e54f061d1a020190198be27");
	}
	
	/**
	 * Metodo que testa com falha o login do usuario
	 * O login falha pois a combinação usuario/senha não existe no banco de dados
	 */
	@Test/*(expected = UsernameNotFoundException.class)*/
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = {DATASET_USUARIOS}, connection = "dataSource")
	@DatabaseTearDown(DATASET_CENARIO_LIMPO)
	public void testLoginMustFail(){
		Usuario usuario = new Usuario();
		usuario.setEmail("victor.blq@gmail.com");
		usuario.setSenha("admin123");
		
		/*usuario = usuarioService.login(usuario);*/
		Assert.fail();
	}
	
	/**
	 * Metodo que testa com sucesso a pesquisa de usuarios
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = {DATASET_USUARIOS}, connection = "dataSource")
	@DatabaseTearDown(DATASET_CENARIO_LIMPO)
	public void testVerificaEmailJaCadastradoMustPass(){
		Usuario usuario = new Usuario();
		
		usuario.setNomeUsuario("Victor");
		List<Usuario> usuarios = usuarioService.find(usuario);
		Assert.assertEquals(usuarios.size(), 1);
		Assert.assertEquals(usuarios.get(0).getNomeUsuario(), "Victor Carvalho");
		
		usuario = new Usuario();
		usuario.setEmail("gmail");
		usuarios = usuarioService.find(usuario);
		Assert.assertEquals(usuarios.size(), 2);
	}
}
