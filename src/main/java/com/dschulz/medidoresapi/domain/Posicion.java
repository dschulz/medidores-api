package com.dschulz.medidoresapi.domain;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;


@Embeddable
public class Posicion {
	@NotNull
	private Double latitud;
	
	@NotNull
	private Double longitud;
	
	@NotNull
	private Double altitud;

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public Double getAltitud() {
		return altitud;
	}

	public void setAltitud(Double altitud) {
		this.altitud = altitud;
	}

	@Override
	public String toString() {
		return "Posicion [latitud=" + latitud + ", longitud=" + longitud + ", altitud=" + altitud + "]";
	}
	
}
