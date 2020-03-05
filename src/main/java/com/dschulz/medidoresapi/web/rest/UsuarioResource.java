package com.dschulz.medidoresapi.web.rest;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dschulz.medidoresapi.domain.Organizacion;
import com.dschulz.medidoresapi.domain.Usuario;
import com.dschulz.medidoresapi.event.ResourceCreatedEvent;
import com.dschulz.medidoresapi.exception.ResourceNotFoundException;
import com.dschulz.medidoresapi.service.OrganizacionService;
import com.dschulz.medidoresapi.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private OrganizacionService orgsService;

	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Usuario> registrar(@Valid @RequestBody Usuario usuario, HttpServletResponse response) {
		Optional<Usuario> usuarioGuardado = usuarioService.registrar(usuario);

		if (usuarioGuardado.isPresent()) {
			String nombreEntity = usuario.getClass().getSimpleName();
			String codigo = usuario.getCodigo().toString();
			publisher.publishEvent(new ResourceCreatedEvent(this, response, nombreEntity, codigo));
			return ResponseEntity.status(HttpStatus.CREATED).body(usuarioGuardado.get());
		}

		// Esto no debe pasar nunca
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(usuario);

	}

	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<Usuario> listar() {
		return usuarioService.listar();
	}

	@GetMapping("/{codigoUsuario}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Usuario> buscar(@PathVariable("codigoUsuario") Long codigo) {

		return ResponseEntity.ok(usuarioService.buscar(codigo)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "codigo", codigo)));
	}

	
	@GetMapping("/{codigoUsuario}/organizaciones")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<Organizacion>> organizaciones(@PathVariable("codigoUsuario") Long codigoUsuario) {

		return ResponseEntity.ok(usuarioService.obtenerOrganizaciones(codigoUsuario));
	}

	@PostMapping("/{codigoUsuario}/organizaciones")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<Organizacion>> agregarOrganizacion(@PathVariable("codigoUsuario") Long usuario,
			@RequestBody Long organizacion) {

		// TODO:
		// 1. Revisar si el codigo recibido corresponde a un usuario existente
		// 2. Revisar que el usuario esté habilitado
		// 3. Revisar que el código de organizacion corresponde a una organizacion
		// existente
		// 4. Revisar que la organización este habilitada
		// 5. ???

		Long codOrg = organizacion; //.getCodigo();
		Optional<Organizacion> org = orgsService.buscar(codOrg);
		Optional<Usuario> usu = usuarioService.buscar(usuario);

		usu.orElseThrow(() -> new ResourceNotFoundException("Usuario", "codigo", usuario));
		org.orElseThrow(() -> new ResourceNotFoundException("Organizacion", "codigo", codOrg));

		if (org.isPresent() && usu.isPresent()) {
			Organizacion o = org.get();
			Usuario u = usu.get();
			o.agregarUsuario(u);
			u.agregarOrganizacion(o);
			usuarioService.agregarOrganizacion(usuario, org.get().getCodigo());
		}

		List<Organizacion> lista = usuarioService.obtenerOrganizaciones(usuario);

		return ResponseEntity.ok(lista);
	}

	@DeleteMapping("/{codigoUsuario}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Usuario> eliminar(@PathVariable("codigoUsuario") Long codigo) {

		Usuario copia = usuarioService.buscar(codigo)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "codigo", codigo));

		usuarioService.eliminar(codigo);

		return ResponseEntity.status(HttpStatus.GONE).header("Mensaje", "Usuario eliminado").body(copia);
	}

}