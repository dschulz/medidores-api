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
@Table(name = "permiso", indexes = { @Index(name = "idx_permiso_nombre", columnList = "nombre", unique = true) })
public class Permiso extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permiso_generator")
	@GenericGenerator(name = "permiso_generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "permiso_sequence"),
			@Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "3"),
			@Parameter(name = "optimizer", value = "pooled-lo") })
	private Long codigo;

	@Column(unique = true, updatable = false)
	@NotBlank(message = "Se requiere un nombre para el permiso")
	private String nombre;

	@Column(unique = true)
	@NotBlank(message = "Se requiere una descripción del permiso")
	private String descripcion;
}
