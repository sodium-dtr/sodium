package space.sodium.exception;

public class RepresentationException extends RuntimeException {

    public RepresentationException(String message) {
        super(message);
    }

    public RepresentationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
