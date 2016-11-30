package com.duayres.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="membro",
uniqueConstraints = { @UniqueConstraint(columnNames = { "usuario_id", "agendamento_id" }) })
public class Membro {

	@Id
	private Long id;
	
	@NotNull
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
	Usuario usuario;
	
	@NotNull
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "agendamento_id", nullable = false)
	Agendamento agendamento;
	
}
