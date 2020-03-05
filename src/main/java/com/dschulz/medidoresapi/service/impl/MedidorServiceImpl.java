package com.dschulz.medidoresapi.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dschulz.medidoresapi.domain.Medidor;
import com.dschulz.medidoresapi.domain.Organizacion;
import com.dschulz.medidoresapi.exception.ResourceAlreadyExistsException;
import com.dschulz.medidoresapi.exception.ResourceNotFoundException;
import com.dschulz.medidoresapi.repository.MedidorRepository;
import com.dschulz.medidoresapi.service.MedidorService;

@Service
public class MedidorServiceImpl implements MedidorService {

	@Autowired
	private MedidorRepository medidorRepository;
	
	@Override
	public Medidor registrar(Medidor medidor) throws ResourceAlreadyExistsException {
		
		Long count = medidorRepository.countByNumeroAndOrganizacion(medidor.getNumero(), medidor.getOrganizacion());
		if (count>0) {
			throw new ResourceAlreadyExistsException("Medidor", "numero", medidor.getNumero());
		}
	
		return medidorRepository.save(medidor);
	}


	@Override
	public List<Medidor> listar() {
		return medidorRepository.findAll();
	}


	@Override
	public Medidor buscar(Long codigo) throws ResourceNotFoundException {
		
		return medidorRepository.findByCodigo(codigo).orElseThrow(() ->  new ResourceNotFoundException("Medidor", "codigo", codigo)  );
	}

	@Override
	public Medidor buscar(String uuidStr) throws ResourceNotFoundException {
		
		UUID uuid = UUID.fromString(uuidStr);
		
		return medidorRepository.findByUuid(uuid).orElseThrow(() ->  new ResourceNotFoundException("Medidor", "uuid", uuid)  );
	}


	@Override
	public List<Medidor> pertenecienteA(Organizacion org) {
		return medidorRepository.findAllByOrganizacion(org);
	}


	
	
}

