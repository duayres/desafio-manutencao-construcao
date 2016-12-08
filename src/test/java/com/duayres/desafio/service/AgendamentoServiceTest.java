package com.duayres.desafio.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.duayres.model.Agendamento;
import com.duayres.repository.IUsuarioRepository;
import com.duayres.service.AgendamentoService;
import com.duayres.service.UsuarioService;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

public class AgendamentoServiceTest {
	public static final String DATASET_CENARIO_LIMPO = "classpath:datasets/AbstractDataset.xml";
	public static final String DATASET_USUARIOS = "classpath:datasets/UsuarioDataset.xml";
	public static final String DATASET_AGENDAMENTO = "classpath:datasets/AgendamentoDataset.xml";
	public static final String DATASET_MEMBRO = "classpath:datasets/MembroDataset.xml";
	public static final String DATASET_LOCALIZACAO = "classpath:datasets/LocalizacaoDataset.xml";
	public static final String DATASET_TIPO_EQUIPAMENTO = "classpath:datasets/TipoDeEquipamentoDataset.xml";
	
	@Autowired(required=false)
	private AgendamentoService agendamentoService;
	
	/**
	 * teste com a marci... apagar
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = {DATASET_LOCALIZACAO, DATASET_TIPO_EQUIPAMENTO, DATASET_AGENDAMENTO, DATASET_USUARIOS, DATASET_MEMBRO}, connection = "dataSource")
	@DatabaseTearDown(DATASET_CENARIO_LIMPO)
	public void testFindAgendamentoByIdMustPass(){
		
		final Agendamento agendamento = this.agendamentoService.findByIdAgendamento( 1L );
		
		Assert.assertNotNull(agendamento);
	}
}
