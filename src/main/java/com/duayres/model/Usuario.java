package com.duayres.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.ObjectError;

/**
 * @author Eduardo Ayres
 *
 */
@Entity
@Table(name = "usuario")
@DataTransferObject/*(type="hibernate3")*/
public class Usuario implements Serializable {


	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long idUsuario;
	
	@NotBlank(message="O nome não pode estar em branco.") //NotEmpty não se mostrou eficiente pois não "trimma" o dado
	@Column(name = "nome", nullable = false, length = 150)
	private String nome;
	
	@NotBlank(message="O Email não pode estar em branco.")
	@Email(message="Você digitou um email inválido. Favor conferir.")//validacao automatica de email
	@Column(nullable = false, unique = true, length = 150)
	private String email;
	
	@NotBlank(message="A senha não pode estar em branco.") //atentar que será criptografada (oneway: hashcode)
	@Column(length=60, nullable = false)
	private String senha;
	
	@Transient //validacao transiente de confirmação de senha (senha1 = senha2 ? :)
	private String confSenha;

	@Transient
	private List<ObjectError> errors;
	
	@Transient
	private String exception;
	
	@Column(name = "status", nullable = false)
	private Boolean status;	
	
	@Column(name = "tipo_usuario", nullable = false)
	@Enumerated
	private TipoUsuario tipoUsuario;	
	



	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Boolean getStatus() {
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

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		return true;
	}

}
