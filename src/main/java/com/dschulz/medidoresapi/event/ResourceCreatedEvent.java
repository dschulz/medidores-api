package com.dschulz.medidoresapi.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class ResourceCreatedEvent  extends ApplicationEvent {

	private static final long serialVersionUID = -4004007961733968633L;
	
	private HttpServletResponse response;
	private String codigo;
	private String entityName;
	
	public ResourceCreatedEvent(Object source, HttpServletResponse response, String entityName , String codigo) {
		super(source);
		
		this.entityName=entityName;
		this.codigo=codigo;
		this.response=response;
		
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getEntityName() {
		return entityName;
	}

	

}
