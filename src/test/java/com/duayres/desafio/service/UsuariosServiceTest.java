package com.duayres.desafio.service;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/*import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;*/
import com.duayres.desafio.AbstractIntegrationTest;
import com.duayres.model.Agendamento;
import com.duayres.model.TipoUsuario;
import com.duayres.model.Usuario;
import com.duayres.repository.IUsuarioRepository;
import com.duayres.service.AgendamentoService;
import com.duayres.service.UsuarioService;
import com.duayres.service.exception.EmailJaExistenteException;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
/*import com.duayres.service.exception.EmailJaCadastradoException;*/
import com.github.springtestdbunit.annotation.DatabaseTearDown;

public class UsuariosServiceTest extends AbstractIntegrationTest{
	public static final String DATASET_CENARIO_LIMPO = "classpath:datasets/AbstractDataset.xml";
	public static final String DATASET_USUARIOS = "classpath:datasets/UsuarioDataset.xml";

	@Autowired(required = false)
	private UsuarioService usuarioService;
	
	@Autowired(required = false)
	private IUsuarioRepository usuarioRepository;

	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = {DATASET_USUARIOS}, connection = "dataSource")
	@DatabaseTearDown(DATASET_CENARIO_LIMPO)
	public void testListAllUsuariosMustPass(){
		this.authenticate(4L);
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
		this.authenticate(4L);
		Usuario user = new Usuario();
		user.setIdUsuario(null);
		user.setNome("Mauricio de Souza");
		user.setEmail("mauricio.tdm@gmail.com");
		user.setSenha("cebolinha");
		user.setConfSenha("cebolinha");
		user.setStatus(true);
		user.setTipoUsuario(TipoUsuario.COLABORADOR);
		
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
	@Test(expected = EmailJaExistenteException.class)
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = {DATASET_USUARIOS}, connection = "dataSource")
	@DatabaseTearDown(DATASET_CENARIO_LIMPO)
	public void testCadastrarUsuariosMustFail(){
		this.authenticate(4L);
		Usuario user = new Usuario();
		user.setIdUsuario(null);
		user.setNome("Marcel Micheletto");
		user.setEmail("eduardo@eduardoayres.com");
		user.setSenha("admin");
		user.setConfSenha("admin");
		user.setStatus(true);
		user.setTipoUsuario(TipoUsuario.COLABORADOR);
		
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
		this.authenticate(4L);
		Usuario user = this.usuarioRepository.findAll().get(0);//this.usuarioRepository.findOne(Long.parseLong("1"));
		
		Assert.assertEquals(user.getNome(), "Eduardo Ayres");
		
		user.setNome("Outro Eduardo");
		user = usuarioService.save(user);
		
		Assert.assertEquals(user.getNome(), "Outro Eduardo");
	}
	
	/**
	 * Metodo que testa com falha a edição de um usuario
	 * A inserção falha pois o valor nomeUsuario para inserção é nulo
	 */
	@Test(expected = ConstraintViolationException.class)
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = {DATASET_USUARIOS}, connection = "dataSource")
	@DatabaseTearDown(DATASET_CENARIO_LIMPO)
	public void testEditarUsuariosMustFail(){
		this.authenticate(4L);
		
		Optional<Usuario> userOpt = this.usuarioRepository.findByIdUsuario(Long.parseLong("4"));
		
		Usuario user = userOpt.get();
		
		Assert.assertEquals(user.getNome(), "Eduardo Ayres");
		user.setNome(null);
		user = usuarioService.save(user);
		
		System.out.println(user.getNome());
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
		usuario.setEmail("eduardo@eduardoayres.com");
		usuario.setSenha("admin");
		
		usuario = usuarioService.login(usuario);
		Assert.assertEquals(usuario.getEmail(), "eduardo@eduardoayres.com");
		Assert.assertEquals(usuario.getSenha(), "$2a$10$2Ew.Cha8uI6sat5ywCnA0elRRahr91v4amVoNV5G9nQwMCpI3jhvO");
	}
	
	/**
	 * Metodo que testa com falha o login do usuario
	 * O login falha pois a combinação usuario/senha não existe no banco de dados
	 */
	@Test(expected = UsernameNotFoundException.class)
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = {DATASET_USUARIOS}, connection = "dataSource")
	@DatabaseTearDown(DATASET_CENARIO_LIMPO)
	public void testLoginMustFail() throws UsernameNotFoundException{
		Usuario usuario = new Usuario();
		usuario.setEmail("eduardo@eduardoayres.com");
		usuario.setSenha("senhainvalida");
		
		usuario = usuarioService.login(usuario);
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
		
		usuario.setNome("Eduardo");
		List<Usuario> usuarios = usuarioService.find(usuario);
		Assert.assertEquals(usuarios.size(), 1);
		Assert.assertEquals(usuarios.get(0).getNome(), "Eduardo Ayres");
		
		usuario = new Usuario();
		usuario.setEmail("eduardo@");
		usuarios = usuarioService.find(usuario);
		Assert.assertEquals(usuarios.size(), 1);
	}
}
