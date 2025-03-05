package hu.wv.webshopbackend.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message ) {
        super( message );
    }
}
