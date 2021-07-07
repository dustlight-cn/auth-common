package plus.auth.resources.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;

import java.util.*;

public class AuthJwtAuthenticationConverter implements Converter<Jwt, Mono<AbstractOAuth2TokenAuthenticationToken>> {

    @Getter
    @Setter
    private String authorityPrefix = "SCOPE_";

    @Override
    public Mono<AbstractOAuth2TokenAuthenticationToken> convert(Jwt jwt) {
        Map<String, Object> claims = jwt.getClaims();
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        if (claims.containsKey("authorities")) {
            List<String> authoritiesList = jwt.getClaimAsStringList("authorities");
            for (String authority : authoritiesList)
                authorities.add(new SimpleGrantedAuthority(authority));
        }

        JwtAuthenticationToken result = new JwtAuthenticationToken(jwt,authorities,jwt.getClaimAsString("username"));
        return Mono.just(result);
    }

}
