package hu.wv.webshopbackend.exception;

import org.springframework.http.HttpStatus;

public class DuplicateEntityException extends RuntimeException{
    public DuplicateEntityException(String message, HttpStatus conflict) {
        super( message );
    }
}
