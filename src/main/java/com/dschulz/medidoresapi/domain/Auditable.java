package com.dschulz.medidoresapi.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> implements Serializable {

    private static final long serialVersionUID = 1L;
	
	@Version
	@Column(name = "version", nullable = false)
	private Long version;

	@CreatedBy
	@Column(name = "creadoPor", nullable = false, updatable = false)
	private U creadoPor;

	@CreatedDate
	@Column(name = "creado", nullable = false, updatable = false)
	private Instant creado = Instant.now();

	@LastModifiedBy
	@Column(name = "modificadoPor", nullable = false)
	private U modificadoPor;

	@LastModifiedDate
	@Column(name = "ultimaModificacion", nullable = false)
	private Instant ultimaModificacion = Instant.now();
	
	@Column(name = "uuid", nullable = false, updatable = false, unique = true)
	private UUID uuid;
	
	
	@PrePersist
	public void inicializarUuid() {
		if(null == this.uuid) {
			this.uuid=UUID.randomUUID();
		}
	}


	public Long getVersion() {
		return version;
	}


	public void setVersion(Long version) {
		this.version = version;
	}


	public U getCreadoPor() {
		return creadoPor;
	}


	public void setCreadoPor(U creadoPor) {
		this.creadoPor = creadoPor;
	}


	public Instant getCreado() {
		return creado;
	}


	public void setCreado(Instant creado) {
		this.creado = creado;
	}


	public U getModificadoPor() {
		return modificadoPor;
	}


	public void setModificadoPor(U modificadoPor) {
		this.modificadoPor = modificadoPor;
	}


	public Instant getUltimaModificacion() {
		return ultimaModificacion;
	}


	public void setUltimaModificacion(Instant ultimaModificacion) {
		this.ultimaModificacion = ultimaModificacion;
	}


	public UUID getUuid() {
		return uuid;
	}


	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
		Auditable<?> other = (Auditable<?>) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
	
}