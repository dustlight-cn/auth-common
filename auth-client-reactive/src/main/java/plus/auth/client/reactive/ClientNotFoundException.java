package plus.auth.client.reactive;

public class ClientNotFoundException extends AuthException {

    public ClientNotFoundException(String message) {
        super(message);
    }

    public ClientNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
