package com.dschulz.medidoresapi.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;

@Entity
@DynamicUpdate
@Table(indexes = { @Index(name = "idx_organizacion_nombre", columnList = "nombre", unique = true) })
public class Organizacion extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "organizacion_generator"
	)
	@GenericGenerator(
		name = "organizacion_generator",
		strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
		parameters = {
			@Parameter(name = "sequence_name", value = "organizacion_sequence"),
			@Parameter(name = "initial_value", value = "1"),
			@Parameter(name = "increment_size", value = "3"),
			@Parameter(name = "optimizer", value = "pooled-lo")
		}
	)
	private Long codigo;

	@NotBlank(message = "Se requiere un email válido")
	@Column(updatable = false, unique = true)
	@Email(message = "Se requiere un email válido")
	private String email;

	@NotNull(message = "Se requiere especificar el estado")
	private Boolean habilitado;

	@NotBlank(message = "Se requiere un nombre")
	@Length(max = 50, min = 2, message = "Se requiere un nombre con al menos {min} y hasta {max} caracteres")
	private String nombre;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "organizacion_usuario", joinColumns = @JoinColumn(name = "codigo_organizacion"), inverseJoinColumns = @JoinColumn(name = "codigo_usuario"))
	private Set<Usuario> usuarios = new HashSet<>();

	public void agregarUsuario(Usuario usuario) {
		this.usuarios.add(usuario);
		usuario.getOrganizaciones().add(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Organizacion other = (Organizacion) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Transient
	public boolean estaHabilitado() {
		return this.habilitado;
	}

	public Long getCodigo() {
		return codigo;
	}

	public String getEmail() {
		return email;
	}

	public Boolean getHabilitado() {
		return habilitado;
	}

	public String getNombre() {
		return nombre;
	}

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	public void removerUsuario(Usuario usuario) {
		this.usuarios.remove(usuario);
		usuario.getOrganizaciones().remove(this);
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public String toString() {
		return "Organizacion [codigo=" + codigo + ", nombre=" + nombre + ", email=" + email + ", usuarios=" + usuarios
				+ ", habilitado=" + habilitado + "]";
	}

}
