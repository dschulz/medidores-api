package com.dschulz.medidoresapi.service;

import java.util.List;
import java.util.Optional;

import com.dschulz.medidoresapi.domain.Medidor;
import com.dschulz.medidoresapi.domain.Organizacion;
import com.dschulz.medidoresapi.domain.Usuario;
import com.dschulz.medidoresapi.exception.ResourceAlreadyExistsException;
import com.dschulz.medidoresapi.exception.ResourceNotFoundException;

public interface OrganizacionService {

	Optional<Organizacion> buscar(Long codigo) throws ResourceNotFoundException;
	Optional<Organizacion> registrar(Organizacion organizacion) throws ResourceAlreadyExistsException;
	List<Usuario> obtenerUsuarios(Long codigoOrganizacion) throws ResourceNotFoundException;
	List<Medidor> obtenerMedidores(Long codigoOrganizacion) throws ResourceNotFoundException;

	List<Organizacion> listar();
	void eliminar(Long codigo);
}
