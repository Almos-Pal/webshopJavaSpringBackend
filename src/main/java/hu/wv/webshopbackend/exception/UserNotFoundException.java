package hu.wv.webshopbackend.exception;



public class UserNotFoundException  extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
