package com.duayres.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "localizacao")
public class Localizacao {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long idLocalizacao;
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy = "localizacao")
	//@JoinColumn(name="localizacao")
	private List<Agendamento> agendamentos;
}
