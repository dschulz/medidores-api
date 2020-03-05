package com.dschulz.medidoresapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dschulz.medidoresapi.domain.Usuario;

@RepositoryRestResource(collectionResourceRel = "usuario", path = "usuario")
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByEmail(String email);
	Optional<Usuario> findByNombre(String email);
	Optional<Usuario> findByEmailOrNombre(String email, String nombre);
	Optional<Usuario> findByCodigo(Long codigo);
	Optional<Usuario> findByUuid(UUID uuid);
	Optional<Usuario> findByDocumento(String documento);
}

