package com.dschulz.medidoresapi.service;

import java.util.List;
import java.util.Optional;

import com.dschulz.medidoresapi.domain.Organizacion;
import com.dschulz.medidoresapi.domain.Usuario;
import com.dschulz.medidoresapi.exception.ResourceAlreadyExistsException;
import com.dschulz.medidoresapi.exception.ResourceNotFoundException;

public interface UsuarioService {
	Optional<Usuario> buscar(Long codigo) throws ResourceNotFoundException;
	Optional<Usuario> buscar(String uuid) throws ResourceNotFoundException;
	Optional<Usuario> registrar(Usuario usuario) throws ResourceAlreadyExistsException;	
	List<Organizacion> obtenerOrganizaciones(Long codigoUsuario) throws ResourceNotFoundException;
	
	List<Usuario> listar();
	void eliminar(Long codigo);
	void agregarOrganizacion(Long codigoUsuario, Long codigoOrganizacion);

}
