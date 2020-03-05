package com.dschulz.medidoresapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dschulz.medidoresapi.domain.Rol;

@RepositoryRestResource(collectionResourceRel = "rol", path = "rol")
public interface RolRepository extends JpaRepository<Rol, Long> {
	Optional<Rol> findByCodigo(Long codigo);
	Optional<Rol> findByNombre(String nombre);
}

