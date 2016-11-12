package com.duayres.service.exception;

/**
 * Exception lançada caso o email do usuário ja exista no banco 
 * @author Eduardo Ayres
 */
public class EmailJaExistenteException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public EmailJaExistenteException(String message){
		super(message);
	}

}
