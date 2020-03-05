package com.dschulz.medidoresapi.web.rest;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dschulz.medidoresapi.domain.Medidor;
import com.dschulz.medidoresapi.domain.Organizacion;
import com.dschulz.medidoresapi.domain.Usuario;
import com.dschulz.medidoresapi.event.ResourceCreatedEvent;
import com.dschulz.medidoresapi.exception.ResourceNotFoundException;
import com.dschulz.medidoresapi.service.OrganizacionService;

@RestController
@RequestMapping("/api/organizaciones")
public class OrganizacionResource {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private OrganizacionService orgService;

	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Organizacion> registrar(@Valid @RequestBody Organizacion org, HttpServletResponse response) {
		
		Optional<Organizacion> orgGuardada = orgService.registrar(org);

		if (orgGuardada.isPresent()) {
			String nombreEntity = org.getClass().getSimpleName();
			String codigo = org.getCodigo().toString();

			publisher.publishEvent(new ResourceCreatedEvent(this, response, nombreEntity, codigo));
			return ResponseEntity.status(HttpStatus.CREATED).body(orgGuardada.get());
		}

		// Esto no debe pasar nunca
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(org);

	}

	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<Organizacion> listar() {
		return orgService.listar();
	}

	@GetMapping("/{codigoOrganizacion}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Organizacion> buscar(@PathVariable("codigoOrganizacion") Long codigo) {

		Optional<Organizacion> org = orgService.buscar(codigo);

		return ResponseEntity
				.ok(org.orElseThrow(() -> new ResourceNotFoundException("Organizacion", "codigo", codigo)));

	}

	@DeleteMapping("/{codigoOrganizacion}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Organizacion> eliminar(@PathVariable("codigoOrganizacion") Long codigo) {

		/* 
		 * TODO:
		 * 1. Eliminar todas las lecturas y medidores
		 * 2. Eliminar todas las asociaciones con usuarios
		 * 3. ??
		 * 
		 */
		
		
		Organizacion copia = orgService.buscar(codigo)
				.orElseThrow(() -> new ResourceNotFoundException("Organizacion", "codigo", codigo));

		orgService.eliminar(codigo);

		return ResponseEntity.status(HttpStatus.GONE).header("Mensaje", "Organizacion eliminada").body(copia);
	}
	
	@GetMapping("/{codigoOrganizacion}/usuarios")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<Usuario>> listarUsuarios( @PathVariable("codigoOrganizacion") Long codigoOrganizacion){
		return ResponseEntity.ok(orgService.obtenerUsuarios(codigoOrganizacion));
	}
	
	@PostMapping("/{codigoOrganizacion}/usuarios")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<Usuario>> agregarUsuario( @PathVariable("codigoOrganizacion") Long codigoOrganizacion, @RequestBody Long codigoUsuario){
		
		throw new ResourceNotFoundException("Usuario", "codigo", codigoUsuario);
		
		// TODO: Implementar esto
		
		//return ResponseEntity.ok(orgService.obtenerUsuarios(codigoOrganizacion));
	}

	@GetMapping("/{codigoOrganizacion}/medidores")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<Medidor>> listarMedidores( @PathVariable("codigoOrganizacion") Long codigoOrganizacion){
		return ResponseEntity.ok(orgService.obtenerMedidores(codigoOrganizacion));
	}
	
}