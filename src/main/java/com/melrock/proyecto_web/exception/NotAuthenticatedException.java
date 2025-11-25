package com.melrock.proyecto_web.exception;

public class NotAuthenticatedException extends RuntimeException {

    public NotAuthenticatedException() {
        super("No se encontró un usuario autenticado en el contexto");
    }

    public NotAuthenticatedException(String message) {
        super(message);
    }
}
