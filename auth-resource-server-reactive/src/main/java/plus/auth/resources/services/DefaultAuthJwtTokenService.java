package plus.auth.resources.services;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import plus.auth.resources.core.AbstractJwtAuthTokenService;
import plus.auth.resources.core.AuthPrincipal;
import plus.auth.resources.core.DefaultAuthPrincipal;

public class DefaultAuthJwtTokenService extends AbstractJwtAuthTokenService {

    public DefaultAuthJwtTokenService(String jwkSet) {
        super(jwkSet);
    }

    public DefaultAuthJwtTokenService(NimbusReactiveJwtDecoder nimbusReactiveJwtDecoder) {
        super(nimbusReactiveJwtDecoder);
    }

    @Override
    protected AuthPrincipal map(Jwt body) {
        return new DefaultAuthPrincipal(body.getClaimAsStringList("authorities"),
                body.getClaimAsStringList("scope"),
                body.getClaimAsStringList("client_authorities"),
                body.getClaimAsString("user_name"),
                body.getClaimAsString("client_id"),
                Long.valueOf(body.getClaimAsString("username")),
                body);
    }
}
