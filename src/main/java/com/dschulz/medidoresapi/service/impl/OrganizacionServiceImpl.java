package com.dschulz.medidoresapi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dschulz.medidoresapi.domain.Medidor;
import com.dschulz.medidoresapi.domain.Organizacion;
import com.dschulz.medidoresapi.domain.Usuario;
import com.dschulz.medidoresapi.exception.ResourceAlreadyExistsException;
import com.dschulz.medidoresapi.exception.ResourceNotFoundException;
import com.dschulz.medidoresapi.repository.MedidorRepository;
import com.dschulz.medidoresapi.repository.OrganizacionRepository;
import com.dschulz.medidoresapi.service.OrganizacionService;

@Service
public class OrganizacionServiceImpl implements OrganizacionService {

	@Autowired
	private OrganizacionRepository orgRepository;
	
	@Autowired
	private MedidorRepository medidorRepository;
	
	

	@Override
	public Optional<Organizacion> buscar(Long codigo) throws ResourceNotFoundException {
		return orgRepository.findByCodigo(codigo);
	}

	@Override
	public Optional<Organizacion> registrar(Organizacion organizacion) {

		Optional<Organizacion> optOrg = orgRepository.findByNombre(organizacion.getNombre());
		if (optOrg.isPresent()) {
			throw new ResourceAlreadyExistsException("Organizacion", "nombre", organizacion.getNombre());
		}

		return Optional.of(orgRepository.save(organizacion));
	}

	@Override
	public List<Organizacion> listar() {
		return orgRepository.findAll();
	}

	@Override
	public void eliminar(Long codigo) {
		orgRepository.deleteById(codigo);
	}

	@Override
	public List<Usuario> obtenerUsuarios(Long codigoOrganizacion) throws ResourceNotFoundException {
		Optional<Organizacion> org = orgRepository.findByCodigo(codigoOrganizacion);

		org.orElseThrow(() -> new ResourceNotFoundException("Organizacion", "codigo", codigoOrganizacion));

		List<Usuario> lista = new ArrayList<Usuario>();

		lista.addAll(org.get().getUsuarios());

		return lista;
	}

	@Override
	public List<Medidor> obtenerMedidores(Long codigoOrganizacion) throws ResourceNotFoundException {
		Optional<Organizacion> org = orgRepository.findByCodigo(codigoOrganizacion);

		org.orElseThrow(() -> new ResourceNotFoundException("Organizacion", "codigo", codigoOrganizacion));

		return medidorRepository.findAllByOrganizacion(org.get());
	}

}