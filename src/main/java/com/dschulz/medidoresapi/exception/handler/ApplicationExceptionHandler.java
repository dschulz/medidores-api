package com.dschulz.medidoresapi.exception.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dschulz.medidoresapi.exception.ResourceAlreadyExistsException;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
	

	@Autowired
	private MessageSource messageSource;
	
	
	public static class Error {
		private String mensajeUsuario;
		private String mensajeDesarrollador;

		public Error(String mensajeUsuario, String mensajeDesarrollador) {
			super();
			this.mensajeUsuario = mensajeUsuario;
			this.mensajeDesarrollador = mensajeDesarrollador;
		}

		public String getMensajeUsuario() {
			return mensajeUsuario;
		}

		public void setMensajeUsuario(String mensajeUsuario) {
			this.mensajeUsuario = mensajeUsuario;
		}

		public String getMensajeDesarrollador() {
			return mensajeDesarrollador;
		}

		public void setMensajeDesarrollador(String mensajeDesarrollador) {
			this.mensajeDesarrollador = mensajeDesarrollador;
		}
		
	}
	
	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("recurso.inexistente", null, LocaleContextHolder.getLocale());
		String mensajeDesarrollador = ex.toString();
		List<Error> errores = Arrays.asList(new Error(mensagemUsuario, mensajeDesarrollador));
		return handleExceptionInternal(ex, errores, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler({ DataIntegrityViolationException.class } )
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
		String mensajeUsuario = messageSource.getMessage("recurso.operacion-no-permitida", null, LocaleContextHolder.getLocale());
		String mensajeDesarrollador = ExceptionUtils.getRootCauseMessage(ex);
		List<Error> errores = Arrays.asList(new Error(mensajeUsuario, mensajeDesarrollador));
		return handleExceptionInternal(ex, errores, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
	
	// Custom
	@ExceptionHandler({ ResourceAlreadyExistsException.class })
	public ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex, WebRequest request) {
		String mensajeUsuario = ex.getMessage();
		String mensajeDesarrollador = ExceptionUtils.getRootCauseMessage(ex);
		
		
		List<Error> errores = Arrays.asList(new Error(mensajeUsuario, mensajeDesarrollador));
		return handleExceptionInternal(ex, errores, new HttpHeaders(), HttpStatus.CONFLICT, request);
		
	}
	
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return super.handleHttpRequestMethodNotSupported(ex, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return super.handleHttpMediaTypeNotSupported(ex, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return super.handleHttpMediaTypeNotAcceptable(ex, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		
		String mensajeUsuario = messageSource.getMessage("mensaje.invalido", null, LocaleContextHolder.getLocale());
		String mensajeDesarrollador = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		List<Error> errores = Arrays.asList(new Error(mensajeUsuario, mensajeDesarrollador));
		
		
		return handleExceptionInternal(ex, errores, headers, HttpStatus.BAD_REQUEST, request);
		
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return super.handleHttpMessageNotWritable(ex, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Error> errores = crearListaDeErrores(ex.getBindingResult());
		return handleExceptionInternal(ex, errores, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	/*
	 * Crear lista de Errores
	 */
	private List<Error> crearListaDeErrores(BindingResult bindingResult) {
		List<Error> errores = new ArrayList<>();
		
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String mensajeUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensajeDesarrollador = fieldError.toString();
			errores.add(new Error(mensajeUsuario, mensajeDesarrollador));
		}
			
		return errores;
	}


}