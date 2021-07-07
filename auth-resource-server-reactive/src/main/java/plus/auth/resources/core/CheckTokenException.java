package plus.auth.resources.core;

public class CheckTokenException extends RuntimeException {

    public CheckTokenException(String message) {
        super(message);
    }

    public CheckTokenException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
