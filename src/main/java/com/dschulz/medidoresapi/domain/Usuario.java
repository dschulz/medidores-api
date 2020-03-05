package com.dschulz.medidoresapi.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;


@Entity
@DynamicUpdate
@Table(indexes = { @Index(name = "idx_usuario_email", columnList = "email", unique = true),
		@Index(name = "idx_usuario_documento", columnList = "documento", unique = true) }

)
public class Usuario extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_generator")
	@GenericGenerator(name = "usuario_generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "usuario_sequence"),
			@Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "3"),
			@Parameter(name = "optimizer", value = "pooled-lo") })
	private Long codigo;

	@NotBlank(message = "Se requiere el número de documento")
	@Column(updatable = false)
	@Pattern(regexp = "^([0-9]+)$", message = "Solo se aceptan dígitos")
	private String documento;

	@Column(updatable = false, unique = true)
	@Email(message = "Se requiere un email válido")
	@NotNull
	private String email;

	@NotNull(message = "Se requiere especificar el estado")
	private Boolean habilitado;

	@NotBlank(message = "Se requiere un nombre válido")
	@Length(max = 50, min = 2, message = "Se requiere un nombre con al menos {min} y hasta {max} caracteres")
	private String nombre;

	@ManyToMany(mappedBy = "usuarios")
	private Set<Organizacion> organizaciones = new HashSet<Organizacion>();

	@NotNull(message = "Se requiere una contraseña")
	private String passwordHash;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_permiso", joinColumns = @JoinColumn(name = "codigo_usuario"), inverseJoinColumns = @JoinColumn(name = "codigo_permiso"))
	private Set<Permiso> permisos;

	public void agregarOrganizacion(Organizacion organizacion) {
		this.organizaciones.add(organizacion);
		organizacion.getUsuarios().add(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Transient
	public boolean estaInhabilitado() {
		return !this.habilitado;
	}

	public Set<Organizacion> getOrganizaciones() {
		return organizaciones;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	public void removerOrganizacion(Organizacion organizacion) {
		this.organizaciones.remove(organizacion);
		organizacion.getUsuarios().remove(this);
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", codigo=" + codigo + ", email=" + email + ", documento=" + documento
				+ ", habilitado=" + habilitado + "]";
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Set<Permiso> getPermisos() {
		return permisos;
	}

	public void setPermisos(Set<Permiso> permisos) {
		this.permisos = permisos;
	}

	public void setOrganizaciones(Set<Organizacion> organizaciones) {
		this.organizaciones = organizaciones;
	}

}
