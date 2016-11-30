package com.duayres.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Eduardo Ayres
 *
 */
@Entity
@Table(name = "localizacao")
@DataTransferObject/*(type="hibernate3")*/
public class Localizacao {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long idLocalizacao;
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy = "localizacao")
	//@JoinColumn(name="localizacao")
	private List<Agendamento> agendamentos;
	
	@NotBlank(message = "O nome da localização deve ser preenchido.")
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLocalizacao == null) ? 0 : idLocalizacao.hashCode());
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
		Localizacao other = (Localizacao) obj;
		if (idLocalizacao == null) {
			if (other.idLocalizacao != null)
				return false;
		} else if (!idLocalizacao.equals(other.idLocalizacao))
			return false;
		return true;
	}
}
