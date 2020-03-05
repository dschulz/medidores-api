package com.dschulz.medidoresapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dschulz.medidoresapi.domain.Permiso;

@RepositoryRestResource(collectionResourceRel = "permiso", path = "permiso")
public interface PermisoRepository extends JpaRepository<Permiso, Long> {
	Optional<Permiso> findByCodigo(Long codigo);
	Optional<Permiso> findByNombre(String nombre);
}
