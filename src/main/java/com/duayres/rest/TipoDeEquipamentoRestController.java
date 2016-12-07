package com.duayres.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.duayres.model.TipoDeEquipamento;
import com.duayres.service.TipoDeEquipamentoService;

@RestController
@RequestMapping(value="/api/tipodeequipamento")
public class TipoDeEquipamentoRestController {
	@Autowired
	TipoDeEquipamentoService equipamentoService;
	
	/*
	 * Retorna todos os tipos de equipamento cadastrados
	 */
	@RequestMapping(value="/listAll", method=RequestMethod.GET)
	public List<TipoDeEquipamento> listAll(){
		return equipamentoService.listAll();
	}
	
	/*
	 * Altera o status (ativo|inativo) do equipamento
	 * @param Long idTipoDeEquipamento id do equipamento
	 * @param Boolean status O estado do equipamento (true=ativo, false=inativo)
	 * @return json de codigo de sucesso (200)
	 */
	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	@RequestMapping(value="/alter-status", method=RequestMethod.POST)
	public ResponseEntity<TipoDeEquipamento> alterStatus(@RequestParam("id") Long idTipoDeEquipamento, @RequestParam("status") Boolean status){
		TipoDeEquipamento equipamento = equipamentoService.findTipoDeEquipamentoById(idTipoDeEquipamento);
		if (equipamento == null) {
			return new ResponseEntity<TipoDeEquipamento>(HttpStatus.NOT_FOUND);
		}
		equipamento.setStatus(status);
		equipamentoService.save(equipamento);
		return new ResponseEntity<TipoDeEquipamento>(HttpStatus.NO_CONTENT);
	}
	
	/*
	 * Ativa um tipo de equipamento
	 * @param Long idTipoDeEquipamento id do equipamento para ser ativado
	 * @return json c/ codigo de sucesso (200)
	 */
	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	@RequestMapping(value="/activate/{id}", method=RequestMethod.GET)
	public  ResponseEntity<TipoDeEquipamento> activateUser(@PathVariable("id") Long idTipoDeEquipamento){
		TipoDeEquipamento equipamento = equipamentoService.findTipoDeEquipamentoById(idTipoDeEquipamento);
		if (equipamento == null) {
			return new ResponseEntity<TipoDeEquipamento>(HttpStatus.NOT_FOUND);
		}
		equipamento.setStatus(true);
		equipamentoService.save(equipamento);
		return new ResponseEntity<TipoDeEquipamento>(HttpStatus.NO_CONTENT);
	}
	
}
