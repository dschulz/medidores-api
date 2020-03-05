package com.dschulz.medidoresapi.security;

import org.springframework.security.core.AuthenticationException;

/**
 * This exception is thrown in case of a not activated user trying to authenticate.
 */
public class UsuarioNoActivadoException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public UsuarioNoActivadoException(String message) {
        super(message);
    }

    public UsuarioNoActivadoException(String message, Throwable t) {
        super(message, t);
    }
}
