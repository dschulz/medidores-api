package com.dschulz.medidoresapi.web.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dschulz.medidoresapi.domain.Lectura;
import com.dschulz.medidoresapi.domain.Medidor;
import com.dschulz.medidoresapi.domain.Organizacion;
import com.dschulz.medidoresapi.service.LecturaService;
import com.dschulz.medidoresapi.service.MedidorService;
import com.dschulz.medidoresapi.util.GeoJsonUtils;

@RestController
@RequestMapping("/api/geojson")
public class GeoJsonResource {

	@Autowired
	LecturaService lecturaService;
	

	@Autowired
	MedidorService medidorService;
	
	@GetMapping("organizacion/{organizacion}/lecturas")
	public ResponseEntity<?> ubicacionesLecturas(@PathVariable("organizacion") @Valid Organizacion organizacion ){
			
		List<Lectura> lecturas = lecturaService.listarPara(organizacion);
			
		return ResponseEntity.ok(GeoJsonUtils.generarDesdeLecturas(lecturas) );
		
	}
	
	
	@GetMapping("organizacion/{organizacion}/medidores")
	public ResponseEntity<?> ubicacionesMedidoresPorOrganizacion(@PathVariable("organizacion") @Valid Organizacion organizacion ){
			
		List<Medidor> medidores = medidorService.pertenecienteA(organizacion);
			
		return ResponseEntity.ok(GeoJsonUtils.generarDesdeMedidores(medidores) );
		
	}
	
	@GetMapping("medidores")
	public ResponseEntity<?> ubicacionesMedidores(){
			
		List<Medidor> medidores = medidorService.listar();
			
		return ResponseEntity.ok(GeoJsonUtils.generarDesdeMedidores(medidores) );
		
	}
	
}