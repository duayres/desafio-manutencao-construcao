package com.duayres.model;

/**
 * @author Eduardo Ayres
 *
 */
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
