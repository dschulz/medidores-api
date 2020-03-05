package com.dschulz.medidoresapi.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dschulz.medidoresapi.domain.Medidor;
import com.dschulz.medidoresapi.domain.Organizacion;

@RepositoryRestResource(collectionResourceRel = "medidor", path = "medidor")
public interface MedidorRepository extends JpaRepository<Medidor, Long> {

	Optional<Medidor> findByCodigo(Long codigo);
	Optional<Medidor> findByUuid(UUID uuid);
	Optional<Medidor> findByNumero(Integer numero);
	
	Long countByNumeroAndOrganizacion(Integer numero, Organizacion org);
	List<Medidor> findAllByOrganizacion(Organizacion org);

}

