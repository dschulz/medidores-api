package com.dschulz.medidoresapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Recurso ya existe")
public class ResourceAlreadyExistsException extends RuntimeException {
	 private static final long serialVersionUID = 1L;
	 
	 private String resourceName;
	 private String fieldName;
	 private Object fieldValue;

	    public ResourceAlreadyExistsException(String resourceName, String fieldName, Object fieldValue) {
	    	super(String.format("Ya existe un %s con %s: '%s'", resourceName, fieldName, fieldValue));
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
	    
	    public String getMessage() {
	    	return String.format("Ya existe un %s con %s: '%s'", resourceName.toLowerCase(), fieldName, fieldValue);
	    }
}