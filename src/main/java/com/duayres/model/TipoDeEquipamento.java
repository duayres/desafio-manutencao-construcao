package com.duayres.model;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.ObjectError;

/**
 * @author Eduardo Ayres
 *
 */
@Entity
@Table(name = "tipo_equipamento")
@DataTransferObject
public class TipoDeEquipamento implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long idEquipamento;
	
	@NotBlank(message="O nome não pode estar em branco.") //NotEmpty não se mostrou eficiente pois não "trimma" o dado
	@Column(nullable = false, length = 150)
	private String nome;
	
	@NotBlank(message="A descrição não pode estar em branco.")
	private String descricao;

	@Lob
	private byte[] foto;

	@Transient
	private List<ObjectError> errors;
	
	@Transient
	private String exception;
	
	private Boolean status;

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BufferedImage getFoto() {
		InputStream in = new ByteArrayInputStream(this.foto);
        try {
			return ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();//@todo fazer melhor
		}
		return null;
	}

	public void setFoto(BufferedImage foto) {
        BufferedOutputStream saida = new ByteArrayOutputStream();
        ImageIO.write(foto, "JPG" /* for instance */, saida);
        this.foto = saida.toByteArray();
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
		result = prime * result + ((idEquipamento == null) ? 0 : idEquipamento.hashCode());
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
		TipoDeEquipamento other = (TipoDeEquipamento) obj;
		if (idEquipamento == null) {
			if (other.idEquipamento != null)
				return false;
		} else if (!idEquipamento.equals(other.idEquipamento))
			return false;
		return true;
	}

}
