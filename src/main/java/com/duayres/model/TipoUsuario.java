package com.duayres.model;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author Eduardo Ayres
 *
 */
@DataTransferObject(type="enum")
public enum TipoUsuario {
	
	ADMINISTRADOR("Administrador"),
	COLABORADOR("Colaborador");
	
	private String tipo;
	
	
	TipoUsuario(String tipo){
		this.tipo = tipo;
	}
	
	public String getTipoUsuario(){
		return this.tipo;
	}
}
