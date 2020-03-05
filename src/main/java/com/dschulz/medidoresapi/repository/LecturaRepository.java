package com.dschulz.medidoresapi.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dschulz.medidoresapi.domain.Lectura;
import com.dschulz.medidoresapi.domain.Medidor;

@RepositoryRestResource(collectionResourceRel = "lectura", path = "lectura")
public interface LecturaRepository extends JpaRepository<Lectura, Long> {
	
	Optional<Lectura> findByCodigo(Long codigo);
	Optional<Lectura> findByUuid(UUID uuid);
	List<Lectura> findByMedidor(Medidor medidor);

}

