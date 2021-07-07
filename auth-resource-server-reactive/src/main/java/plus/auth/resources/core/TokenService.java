package plus.auth.resources.core;

import reactor.core.publisher.Mono;

public interface TokenService {

    Mono<AuthPrincipal> check(String token) throws CheckTokenException;
}
