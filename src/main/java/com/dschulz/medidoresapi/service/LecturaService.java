package com.dschulz.medidoresapi.service;

import java.util.List;

import javax.validation.Valid;

import com.dschulz.medidoresapi.domain.Lectura;
import com.dschulz.medidoresapi.domain.Medidor;
import com.dschulz.medidoresapi.domain.Organizacion;
import com.dschulz.medidoresapi.exception.ResourceAlreadyExistsException;
import com.dschulz.medidoresapi.exception.ResourceNotFoundException;

public interface LecturaService {
	Lectura buscar(Long codigo) throws ResourceNotFoundException;
	Lectura buscar(String uuid) throws ResourceNotFoundException;
	Lectura registrar(@Valid Lectura lectura) throws ResourceAlreadyExistsException;
	List<Lectura> listarPara(Medidor medidor);
	List<Lectura> listarPara(Organizacion organizacion);

}
