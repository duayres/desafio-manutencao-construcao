package com.duayres.desafio.service;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import com.duayres.desafio.AbstractIntegrationTest;
import com.duayres.model.TipoDeEquipamento;
import com.duayres.service.TipoDeEquipamentoService;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

public class TipoDeEquipamentoServiceTest extends AbstractIntegrationTest{
	public static final String DATASET_CENARIO_LIMPO = "classpath:datasets/AbstractDataset.xml";
	public static final String DATASET_EQUIPAMENTOS = "classpath:datasets/TipoDeEquipamentoDataset.xml";
	public static final String DATASET_USUARIOS = "classpath:datasets/UsuarioDataset.xml";
	
	@Autowired(required = false)
	private TipoDeEquipamentoService tipoDeEquipamentoService;

	
	/**
	 * Metodo que testa com sucesso a consulta de todos os tipos de equipamento
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = {DATASET_EQUIPAMENTOS,DATASET_USUARIOS}, connection = "dataSource")
	@DatabaseTearDown(DATASET_CENARIO_LIMPO)
	public void testListAllTipoDeEquipamentosMustPass(){
		this.authenticate(4L);
		List<TipoDeEquipamento> tiposDeEquipamento = tipoDeEquipamentoService.listAll();
		
		Assert.assertNotNull(tiposDeEquipamento);
		Assert.assertEquals(tiposDeEquipamento.size(), 2);
	}
	
	/**
	 * Metodo que testa com sucesso a inserção de um tipo de equipamento
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = {DATASET_EQUIPAMENTOS,DATASET_USUARIOS}, connection = "dataSource")
	@DatabaseTearDown(DATASET_CENARIO_LIMPO)
	public void testCadastrarTipoDeEquipamentosMustPass(){
		this.authenticate(4L);
		TipoDeEquipamento tipoDeEquipamento = new TipoDeEquipamento();
		tipoDeEquipamento.setIdEquipamento(100L);
		tipoDeEquipamento.setNome("Geladeira frost-free 315L");
		tipoDeEquipamento.setDescricao("Geladeira frost-free com 315 litros");
		tipoDeEquipamento.setFoto("UIIIDUNICOAQUI_SemNome.jpg");
		tipoDeEquipamento.setManual("UIIIDUNICOAQUI_SemNome.pdf");
		tipoDeEquipamento.setStatus(true);//não excluida
		
		tipoDeEquipamento = tipoDeEquipamentoService.save(tipoDeEquipamento);
		
		Assert.assertNotNull(tipoDeEquipamento.getIdEquipamento());
		List<TipoDeEquipamento> tiposDeEquipamento = tipoDeEquipamentoService.listAll();
		Assert.assertNotNull(tiposDeEquipamento);
		Assert.assertEquals(tiposDeEquipamento.size(), 3);
		
	}
	
	/**
	 * Metodo que testa com falha a inserção de um tipo de equipamento
	 * A inserção falha pois o atributo de nome não é preenchido
	 */
	@Test(expected = ConstraintViolationException.class)
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = {DATASET_EQUIPAMENTOS,DATASET_USUARIOS}, connection = "dataSource")
	@DatabaseTearDown(DATASET_CENARIO_LIMPO)
	public void testCadastrarTipoDeEquipamentosMustFail(){
		this.authenticate(4L);
		TipoDeEquipamento tipoDeEquipamento = new TipoDeEquipamento();
		tipoDeEquipamento.setDescricao("Geladeira frost-free com 315 litros");
		tipoDeEquipamento.setStatus(true);//não excluida

		
		tipoDeEquipamento = tipoDeEquipamentoService.save(tipoDeEquipamento);
		
		Assert.fail();
	}
	
	/**
	 * Metodo que testa com sucesso a edição de um tipo de equipamento
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = {DATASET_EQUIPAMENTOS,DATASET_USUARIOS}, connection = "dataSource")
	@DatabaseTearDown(DATASET_CENARIO_LIMPO)
	public void testEditarTipoDeEquipamentosMustPass(){
		this.authenticate(4L);
		TipoDeEquipamento user = this.tipoDeEquipamentoService.listAll().get(0);
		
		Assert.assertEquals(user.getNome(), "Notebook Dell");
		
		user.setNome("Notebook Acer");
		user = tipoDeEquipamentoService.save(user);
		
		Assert.assertEquals(user.getNome(), "Notebook Acer");
	}
	
	/**
	 * Metodo que testa com falha a edição de um tipo de Equipamemento
	 * A inserção falha pois o valor nome para inserção é nulo
	 */
	@Test(expected = ConstraintViolationException.class)
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = {DATASET_EQUIPAMENTOS,DATASET_USUARIOS}, connection = "dataSource")
	@DatabaseTearDown(DATASET_CENARIO_LIMPO)
	public void testEditarTipoDeEquipamentosMustFail(){
		this.authenticate(4L);
		
		TipoDeEquipamento tipoDeEquipamento = this.tipoDeEquipamentoService.findTipoDeEquipamentoById(Long.parseLong("1"));

		Assert.assertEquals(tipoDeEquipamento.getNome(), "Notebook Dell");
		tipoDeEquipamento.setNome(null);
		tipoDeEquipamento = tipoDeEquipamentoService.save(tipoDeEquipamento);
		
		System.out.println(tipoDeEquipamento.getNome());
		Assert.fail();
	}

	/**
	 * Método que testa com sucesso exclusão (lógica) do tipo de equipamento
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = {DATASET_EQUIPAMENTOS,DATASET_USUARIOS}, connection = "dataSource")
	@DatabaseTearDown(DATASET_CENARIO_LIMPO)
	public void testExcluirTipoDeEquipamentosMustPass(){
		this.authenticate(4L);

		try {
			tipoDeEquipamentoService.logicExclusion(Long.parseLong("1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Método que testa com falha a exclusão (lógica) do tipo de equipamento
	 * A inserção falha pois o usuário que foi autenticado não é um administrador
	 */
	@Test(expected=AccessDeniedException.class)
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = {DATASET_EQUIPAMENTOS,DATASET_USUARIOS}, connection = "dataSource")
	@DatabaseTearDown(DATASET_CENARIO_LIMPO)
	public void testExcluirTipoDeEquipamentosMustFail(){
		this.authenticate(5L);

		tipoDeEquipamentoService.logicExclusion(Long.parseLong("1"));

		Assert.fail();
	}
}
