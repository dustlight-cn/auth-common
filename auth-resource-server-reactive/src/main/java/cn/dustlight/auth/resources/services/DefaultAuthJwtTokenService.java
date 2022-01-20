package cn.dustlight.auth.resources.services;

import cn.dustlight.auth.resources.core.AbstractJwtAuthTokenService;
import cn.dustlight.auth.resources.core.AuthPrincipal;
import cn.dustlight.auth.resources.core.DefaultAuthPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;

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
                body.getClaimAsBoolean("member"),
                Long.valueOf(body.getClaimAsString("username")),
                body,
                body.getTokenValue());
    }
}
