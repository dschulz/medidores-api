package com.dschulz.medidoresapi.domain;


import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


@Entity
public class Lectura extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "lectura_generator"
	)
	@GenericGenerator(
		name = "lectura_generator",
		strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
		parameters = {
			@Parameter(name = "sequence_name", value = "lectura_sequence"),
			@Parameter(name = "initial_value", value = "1"),
			@Parameter(name = "increment_size", value = "3"),
			@Parameter(name = "optimizer", value = "pooled-lo")
		}
	)
	Long codigo;
	

	@NotNull(message = "Se requiere la lectura del contador del medidor")
	private Integer contador;
	
	@NotNull
	private Instant instante = Instant.now();
	
	@NotNull (message = "Se requiere el identificador único del medidor")
	@ManyToOne
	@JoinColumn(name= "medidor_codigo" , nullable = false)
	private Medidor medidor;
	
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
		Lectura other = (Lectura) obj;
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

	public Integer getContador() {
		return contador;
	}

	public Instant getInstante() {
		return instante;
	}

	public Medidor getMedidor() {
		return medidor;
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

	public void setContador(Integer contador) {
		this.contador = contador;
	}

	public void setInstante(Instant instante) {
		this.instante = instante;
	}

	public void setMedidor(Medidor medidor) {
		this.medidor = medidor;
	}

	public void setPosicion(Posicion posicion) {
		this.posicion = posicion;
	}

	@Override
	public String toString() {
		return "Lectura [codigo=" + codigo + ", medidor=" + medidor + ", contador=" + contador + ", instante="
				+ instante + ", posicion=" + posicion + "]";
	}

}
