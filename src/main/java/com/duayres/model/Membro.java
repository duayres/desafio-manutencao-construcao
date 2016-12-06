package com.duayres.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;

@Entity
@Table(name="membro",
uniqueConstraints = { @UniqueConstraint(columnNames = { "usuario_id", "agendamento_id" }) })
@DataTransferObject
public class Membro {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;
	
	@NotNull
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name = "agendamento_id", nullable = false)
	private Agendamento agendamento;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}
	
}
