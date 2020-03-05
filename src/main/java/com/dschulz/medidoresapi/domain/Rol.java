package com.dschulz.medidoresapi.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


@DynamicUpdate
@Entity
@Table(name = "rol", indexes = { @Index(name = "idx_rol_nombre", columnList = "nombre", unique = true) })
public class Rol extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rol_generator")
	@GenericGenerator(name = "rol_generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "rol_sequence"),
			@Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "3"),
			@Parameter(name = "optimizer", value = "pooled-lo") })
	private Long codigo;

	@Column(unique = true)
	@NotBlank(message = "Se requiere una descripción del rol")
	private String descripcion;

	@Column(unique = true, updatable = false)
	@NotBlank(message = "Se requiere un nombre para el rol")
	private String nombre;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rol other = (Rol) obj;
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

	public String getDescripcion() {
		return descripcion;
	}

	public String getNombre() {
		return nombre;
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

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Rol [codigo=" + codigo + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
	}
}
