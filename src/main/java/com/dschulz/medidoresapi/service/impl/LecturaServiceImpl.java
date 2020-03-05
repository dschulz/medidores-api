package com.dschulz.medidoresapi.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dschulz.medidoresapi.domain.Lectura;
import com.dschulz.medidoresapi.domain.Medidor;
import com.dschulz.medidoresapi.domain.Organizacion;
import com.dschulz.medidoresapi.exception.ResourceNotFoundException;
import com.dschulz.medidoresapi.repository.LecturaRepository;
import com.dschulz.medidoresapi.service.LecturaService;

@Service
public class LecturaServiceImpl implements LecturaService {

	@Autowired
	LecturaRepository lecturaRepository;

	@Override
	public Lectura buscar(Long codigo) throws ResourceNotFoundException {
		Optional<Lectura> encontrada = lecturaRepository.findByCodigo(codigo);

		encontrada.orElseThrow(() -> new ResourceNotFoundException("Lectura", "uuid", codigo));

		return encontrada.get();
	}

	@Override
	public Lectura buscar(String uuid) throws ResourceNotFoundException {
		UUID aBuscar = UUID.fromString(uuid);

		Optional<Lectura> encontrada = lecturaRepository.findByUuid(aBuscar);

		encontrada.orElseThrow(() -> new ResourceNotFoundException("Lectura", "uuid", aBuscar));

		return encontrada.get();
	}

	@Override
	public Lectura registrar(@Valid Lectura lectura) {

		return lecturaRepository.save(lectura);
	}

	@Override
	public List<Lectura> listarPara(Medidor medidor) {
		return lecturaRepository.findByMedidor(medidor);
	}

	@Override
	public List<Lectura> listarPara(Organizacion organizacion) {
		// Buscar la ultima lectura de cada medidor asociado a una organizacion
		return null;
	}

}
