package com.dschulz.medidoresapi.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class NoResource {

	@RequestMapping
	public ResponseEntity<String> indice() {

		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_PLAIN)
				.body("Nothing to see here, move along");
	}
}
