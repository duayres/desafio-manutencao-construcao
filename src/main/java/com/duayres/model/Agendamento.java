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
import javax.validation.constraints.NotNull;

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
    //@JoinColumn(name = "localizacao", nullable = false, insertable=false, updatable=false)
	private Localizacao localizacao;

	@NotNull
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "tipo_equipamento", nullable = false, insertable=false, updatable=false)
	private TipoDeEquipamento tipoEquipamento;

	@OneToMany(fetch=FetchType.EAGER)
	@JoinTable(name="membro")
	private List<Usuario> membros;

	
	public Calendar getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Calendar dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Calendar getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Calendar dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Localizacao getLocalizacao() {
		return localizacao;
	}

	public TipoDeEquipamento getTipoEquipamento() {
		return tipoEquipamento;
	}

	public void setTipoEquipamento(TipoDeEquipamento tipoEquipamento) {
		this.tipoEquipamento = tipoEquipamento;
	}
	
	public void setLocalizacao(Localizacao localizacao) {
		this.localizacao = localizacao;
	}

	public List<Usuario> getMembros() {
		return membros;
	}

	public void setMembros(List<Usuario> membros) {
		this.membros = membros;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAgendamento == null) ? 0 : idAgendamento.hashCode());
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
		Agendamento other = (Agendamento) obj;
		if (idAgendamento == null) {
			if (other.idAgendamento != null)
				return false;
		} else if (!idAgendamento.equals(other.idAgendamento))
			return false;
		return true;
	}	
}
