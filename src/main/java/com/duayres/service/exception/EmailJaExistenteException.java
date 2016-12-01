package com.duayres.service.exception;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * Exception lançada caso o email do usuário ja exista no banco 
 * @author Eduardo Ayres
 */
public class EmailJaExistenteException extends DataIntegrityViolationException{

	private static final long serialVersionUID = 1L;
	
	public EmailJaExistenteException(String message){
		super(message);
	}

}
