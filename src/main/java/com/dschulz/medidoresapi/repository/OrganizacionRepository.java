package com.dschulz.medidoresapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dschulz.medidoresapi.domain.Organizacion;

@RepositoryRestResource(collectionResourceRel = "organizacion", path = "organizacion")
public interface OrganizacionRepository extends JpaRepository<Organizacion, Long>{
	Optional<Organizacion> findByNombre(String numero);
	Optional<Organizacion> findByCodigo(Long codigo);
}
