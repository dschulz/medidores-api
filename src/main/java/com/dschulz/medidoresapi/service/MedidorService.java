package com.dschulz.medidoresapi.service;

import java.util.List;

import com.dschulz.medidoresapi.domain.Medidor;
import com.dschulz.medidoresapi.domain.Organizacion;
import com.dschulz.medidoresapi.exception.ResourceAlreadyExistsException;
import com.dschulz.medidoresapi.exception.ResourceNotFoundException;

public interface MedidorService {
	Medidor buscar(Long codigo) throws ResourceNotFoundException;
	Medidor buscar(String uuid) throws ResourceNotFoundException;
	Medidor registrar(Medidor medidor) throws ResourceAlreadyExistsException;	
	List<Medidor> listar();
	List<Medidor> pertenecienteA(Organizacion org);
	
}
