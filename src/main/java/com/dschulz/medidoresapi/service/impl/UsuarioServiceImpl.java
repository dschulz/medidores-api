package com.dschulz.medidoresapi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dschulz.medidoresapi.domain.Organizacion;
import com.dschulz.medidoresapi.domain.Usuario;
import com.dschulz.medidoresapi.exception.ResourceAlreadyExistsException;
import com.dschulz.medidoresapi.exception.ResourceNotFoundException;
import com.dschulz.medidoresapi.repository.OrganizacionRepository;
import com.dschulz.medidoresapi.repository.UsuarioRepository;
import com.dschulz.medidoresapi.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private OrganizacionRepository orgsRepository;


	@Override
	public Optional<Usuario> registrar(Usuario usuario) throws ResourceAlreadyExistsException {
		Optional<Usuario> ou = usuarioRepository.findByEmail(usuario.getEmail());
		if (ou.isPresent()) {
			throw new ResourceAlreadyExistsException("Usuario", "email", usuario.getEmail());
		}

		ou = usuarioRepository.findByDocumento(usuario.getDocumento());
		if (ou.isPresent()) {
			throw new ResourceAlreadyExistsException("Usuario", "documento", usuario.getDocumento());
		}

		return Optional.of(usuarioRepository.save(usuario));
	}

	@Override
	public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}

	@Override
	public Optional<Usuario> buscar(Long codigo) throws ResourceNotFoundException {

		return usuarioRepository.findByCodigo(codigo);
	}

	@Override
	public Optional<Usuario> buscar(String uuid) throws ResourceNotFoundException {
		UUID uuidObj = UUID.fromString(uuid);

		return Optional.of(usuarioRepository.findByUuid(uuidObj))
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "uuid", uuidObj));
	}

	@Override
	public List<Organizacion> obtenerOrganizaciones(Long codigoUsuario) throws ResourceNotFoundException {

		Optional<Usuario> u = usuarioRepository.findByCodigo(codigoUsuario);

		if (u.isEmpty()) {
			throw new ResourceNotFoundException("Usuario", "codigo", codigoUsuario);
		}

		List<Organizacion> lista = new ArrayList<Organizacion>();

		lista.addAll(u.get().getOrganizaciones());

		return lista;
	}

	@Override
	public void eliminar(Long codigo) {
		usuarioRepository.deleteById(codigo);
	}

	@Override
	public void agregarOrganizacion(Long codigoUsuario, Long codigoOrganizacion) {
		Optional<Usuario> u = usuarioRepository.findByCodigo(codigoUsuario);
		

		if (u.isEmpty()) {
			throw new ResourceNotFoundException("Usuario", "codigo", codigoUsuario);
		}
		
		Optional<Organizacion> o = orgsRepository.findByCodigo(codigoOrganizacion);
		if (o.isEmpty()) {
			throw new ResourceNotFoundException("Organizacion", "codigo", codigoOrganizacion);
		}
		
		o.get().agregarUsuario(u.get());
        u.get().agregarOrganizacion(o.get());
        
        orgsRepository.save(o.get());
        usuarioRepository.save(u.get());
		
	}

}
