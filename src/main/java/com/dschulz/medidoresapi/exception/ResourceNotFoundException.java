package com.dschulz.medidoresapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Recurso no encontrado")
public class ResourceNotFoundException extends RuntimeException {
	 private static final long serialVersionUID = 1L;
	 
	 private String resourceName;
	 private String fieldName;
	 private Object fieldValue;

	    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
	    	super(String.format("Recurso de tipo '%s' con %s [%s] no registrado", resourceName, fieldName, fieldValue));
	    	this.resourceName=resourceName;
	    	this.fieldName=fieldName;
	    	this.fieldValue=fieldValue;
	    }
	    
	    public String getResourceName() {
	    	return resourceName;
	    }
	    
	    public String getFieldName() {
	    	return fieldName;
	    }
	    
	    public Object getFieldValue() {
	    	return fieldValue;
	    }
}