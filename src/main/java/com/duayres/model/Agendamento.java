package com.duayres.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Eduardo Ayres
 *
 */
@Entity
@Table(name = "agendamento")
public class Agendamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long idAgendamento;
	
	@Column(name = "data_inicial")
	private Calendar dataInicial;

	@Column(name = "data_final")
	private Calendar dataFinal;
	
	@ManyToOne(fetch=FetchType.EAGER)//(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name = "localizacao", nullable = false, insertable=false, updatable=false)
	private Localizacao localizacao;
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinTable(name="membro")
	private List<Usuario> membros;
	
}
