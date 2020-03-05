package com.dschulz.medidoresapi.event.listener;


import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dschulz.medidoresapi.event.ResourceCreatedEvent;


@Component
public class ResourceCreatedListener implements ApplicationListener<ResourceCreatedEvent> {
	

	@Override
	public void onApplicationEvent(ResourceCreatedEvent recursoCreadoEvent) {
		HttpServletResponse response = recursoCreadoEvent.getResponse();
		String codigo = recursoCreadoEvent.getCodigo();
		String entityName = recursoCreadoEvent.getEntityName();
		
		agregarHeaderLocation(response, codigo);
		agregarHeaderEntity(response, entityName );
	}

	private void agregarHeaderEntity(HttpServletResponse response, String entityName) {
		response.setHeader("Entity", entityName);
	}

	private void agregarHeaderLocation(HttpServletResponse response, String codigo) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(codigo).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
	}


}