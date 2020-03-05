package com.dschulz.medidoresapi.domain;


import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


@Entity
@Table(indexes = {
		@Index(name = "idx_medidor_num_unico_para_org", columnList = "numero,organizacion_codigo", unique = true) })
public class Medidor extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Id
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "medidor_generator"
	)
	@GenericGenerator(
		name = "medidor_generator",
		strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
		parameters = {
			@Parameter(name = "sequence_name", value = "medidor_sequence"),
			@Parameter(name = "initial_value", value = "1"),
			@Parameter(name = "increment_size", value = "3"),
			@Parameter(name = "optimizer", value = "pooled-lo")
		}
	)
	private Long codigo;

	@Positive(message = "Debe ser un número válido")
	@NotNull(message = "No puede ser nulo")
	private Integer numero;

	@NotNull(message = "Se requiere el identificador único de la organización")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "organizacion_codigo", nullable = false)
	private Organizacion organizacion;

	@Embedded
	private Posicion posicion;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Medidor other = (Medidor) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	public Long getCodigo() {
		return codigo;
	}

	public Integer getNumero() {
		return numero;
	}

	public Organizacion getOrganizacion() {
		return organizacion;
	}

	public Posicion getPosicion() {
		return posicion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public void setOrganizacion(Organizacion organizacion) {
		this.organizacion = organizacion;
	}

	public void setPosicion(Posicion posicion) {
		this.posicion = posicion;
	}

	@Override
	public String toString() {
		return "Medidor [codigo=" + codigo + ", numero=" + numero + ", organizacion=" + organizacion + ", posicion="
				+ posicion + "]";
	}



}

