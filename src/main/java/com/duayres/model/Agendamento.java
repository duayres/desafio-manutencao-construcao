package com.duayres.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * @author Eduardo Ayres
 *
 */
@Entity
@Table(name = "agendamento")
@DataTransferObject/*(type="hibernate3")*/
public class Agendamento implements Serializable {

	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long idAgendamento;

	@NotNull(message = "Você deve selecionar a data e hora de inicio do agendamento de manutenção.")
	@Column(name = "data_inicial")
	private Calendar dataInicial;

	@NotNull(message = "Você deve selecionar a data e hora de finalização do agendamento.")
	@Column(name = "data_final")
	private Calendar dataFinal;
	
	@NotNull
	@ManyToOne(fetch=FetchType.EAGER)//(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    //@JoinColumn(name = "localizacao", nullable = false)
	private Localizacao localizacao;

	@NotNull(message="O Tipo de equipamento é obrigatório")
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "tipo_equipamento", nullable = false)//, insertable=false, updatable=false)
	private TipoDeEquipamento tipoDeEquipamento;
	
	/**
	 * 
	 */
	@NotNull
	@OneToMany( mappedBy="agendamento", fetch = FetchType.EAGER )
    @OnDelete(action=OnDeleteAction.CASCADE)
	private List<Membro> membros = new ArrayList<Membro>();


	public Long getIdAgendamento() {
		return idAgendamento;
	}

	public void setIdAgendamento(Long idAgendamento) {
		this.idAgendamento = idAgendamento;
	}

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

	public TipoDeEquipamento getTipoDeEquipamento() {
		return tipoDeEquipamento;
	}

	public void setTipoDeEquipamento(TipoDeEquipamento tipoDeEquipamento) {
		this.tipoDeEquipamento = tipoDeEquipamento;
	}
	
	public void setLocalizacao(Localizacao localizacao) {
		this.localizacao = localizacao;
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
	
	
	public Boolean verificaDataInicialPassada() {
		Calendar agora = Calendar.getInstance();
		return dataInicial.before(agora.getTime());
	}
	
	public Boolean verificaDataFinalPassada() {
		Calendar agora = Calendar.getInstance();
		return dataFinal.before(agora.getTime());
	}
	
	public Boolean verificaDataInicialHoje() {
		Calendar agora = Calendar.getInstance();
		return dataInicial.equals(agora.getTime());
	}	
	
	public Boolean verificaDataFinalHoje() {
		Calendar agora = Calendar.getInstance();
		return dataFinal.equals(agora.getTime());
	}
	
}
