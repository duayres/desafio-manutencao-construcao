package com.duayres.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import org.springframework.validation.ObjectError;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer idUsuario;
	
	@NotBlank(message="O nome não pode estar em branco.") //notempty não se mostrou eficiente pois não "trimma" o dado
	@Column(name = "nome", nullable = false, length = 150)
	private String nomeUsuario;
	
	@NotBlank(message="O Email não pode estar em branco.")
	@Email(message="Você digitou um email inválido. Favor conferir.")//validacao automatica de email
	@Column(nullable = false, unique = true, length = 150)
	private String email;
	
	@NotBlank(message="A senha não pode estar em branco.") //atentar que será criptografada (oneway: hashcode)
	@Column(length=50, nullable = false)
	private String senha;
	
	@Transient //validacao transiente de confirmação de senha (senha1 = senha2 ? :)
	private String confSenha;

	@Transient
	private List<ObjectError> errors;
	
	@Transient
	private String exception;
	
	@Column(name = "tipo_usuario", nullable = false)
	private TipoUsuario tipoUsuario;	
	
	private Boolean status;
	

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfSenha() {
		return confSenha;
	}

	public void setConfSenha(String confSenha) {
		this.confSenha = confSenha;
	}

	public List<ObjectError> getErrors() {
		return errors;
	}

	public void setErrors(List<ObjectError> errors) {
		this.errors = errors;
	}

	public TipoUsuario getTipo() {
		return tipoUsuario;
	}

	public void setTipo(TipoUsuario tipo) {
		this.tipoUsuario = tipo;
	}

	public Boolean setStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	
}
