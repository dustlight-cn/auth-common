package cn.dustlight.auth.resources.core;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import reactor.core.publisher.Mono;

public abstract class AbstractJwtAuthTokenService implements TokenService {

    private NimbusReactiveJwtDecoder nimbusReactiveJwtDecoder;

    public AbstractJwtAuthTokenService(String jwkSet) {
        this.nimbusReactiveJwtDecoder = new NimbusReactiveJwtDecoder(jwkSet);
    }

    public AbstractJwtAuthTokenService(NimbusReactiveJwtDecoder nimbusReactiveJwtDecoder) {
        this.nimbusReactiveJwtDecoder = nimbusReactiveJwtDecoder;
    }

    @Override
    public Mono<AuthPrincipal> check(String token) {
        return nimbusReactiveJwtDecoder.decode(token)
                .map(jwt -> map(jwt));
    }

    protected abstract AuthPrincipal map(Jwt body);
}
