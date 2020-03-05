package com.dschulz.medidoresapi.web.rest;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dschulz.medidoresapi.domain.Lectura;
import com.dschulz.medidoresapi.domain.Medidor;
import com.dschulz.medidoresapi.domain.Organizacion;
import com.dschulz.medidoresapi.event.ResourceCreatedEvent;
import com.dschulz.medidoresapi.exception.ResourceNotFoundException;
import com.dschulz.medidoresapi.repository.OrganizacionRepository;
import com.dschulz.medidoresapi.service.LecturaService;
import com.dschulz.medidoresapi.service.MedidorService;

@RestController
@RequestMapping("/api/medidores")
public class MedidorResource {

	

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private OrganizacionRepository organizacionRepository;
	
	@Autowired
	private MedidorService medidorService;
	
	@Autowired
	private LecturaService lecturaService;

	@PostMapping
	public ResponseEntity<Medidor> registrar(@Valid @RequestBody Medidor medidor, HttpServletResponse response) {
		
		
		Long orgId = medidor.getOrganizacion().getCodigo();
		
		Optional<Organizacion> org = organizacionRepository.findById(orgId);
		
		/* TODO: Mejorar esto */
		if(!org.isEmpty()) {
			medidor.setOrganizacion(org.get());
		}else {
			throw new ResourceNotFoundException("Organizacion", "codigo", orgId);

		}
		
		Medidor medidorGuardado = medidorService.registrar(medidor);
	
		String nombreEntity= medidor.getClass().getSimpleName();
		String codigo = medidor.getCodigo().toString();
				
		publisher.publishEvent(new ResourceCreatedEvent(this, response, nombreEntity, codigo ));
		return ResponseEntity.status(HttpStatus.CREATED).body(medidorGuardado);
	}

	@GetMapping
	public List<Medidor> listar() {
		return medidorService.listar();
	}

	@GetMapping("/{codigoMedidor}")
	public ResponseEntity<Medidor> buscar(@PathVariable("codigoMedidor") Long codigo) {

		return ResponseEntity.ok(medidorService.buscar(codigo));
	}
	
	@DeleteMapping("/{codigoMedidor}")
	public ResponseEntity<Medidor> eliminar(@PathVariable("codigoMedidor") Long codigo) {

		/*
		 * TODO:
		 * Eliminar todas las lecturas asociadas al medidor
		 * Eliminar medidor
		 */
		
		// Por ahora no eliminamos nada, solo retornamos el medidor, si existe
		return ResponseEntity.ok(medidorService.buscar(codigo));
	}
	
	
	
	@GetMapping("/{codigoMedidor}/lecturas")
	public ResponseEntity<List<Lectura>> listar(@PathVariable("codigoMedidor") Medidor medidor) {
		return ResponseEntity.ok(lecturaService.listarPara(medidor));
	}

	@PostMapping("/{codigoMedidor}/lecturas")
	public ResponseEntity<Lectura> nueva(@PathVariable("codigoMedidor") Medidor medidor, @RequestBody @Valid Lectura lectura) {
		
	
		System.err.println("Lectura: " + lectura.toString() + " Medidor: " + medidor );	
		
		// TODO:
		// Validar lectura (definir reglas)
		
		lectura.setMedidor(medidor);
		return ResponseEntity.ok( lecturaService.registrar(lectura));
	}
	
	@GetMapping("/{codigoMedidor}/organizacion")
	public ResponseEntity<Organizacion> organizacion(@PathVariable("codigoMedidor") Medidor medidor) {
		return ResponseEntity.ok(medidor.getOrganizacion());
	}
	
	
}

