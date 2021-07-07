package plus.auth.resources.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionClaimNames;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.*;

public class AuthJwtAuthenticationConverter implements Converter<Jwt, Mono<AbstractOAuth2TokenAuthenticationToken>> {

    @Getter
    @Setter
    private String authorityPrefix = "SCOPE_";

    @Override
    public Mono<AbstractOAuth2TokenAuthenticationToken> convert(Jwt jwt) {
        Map<String, Object> claims = jwt.getClaims();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (jwt.getAudience() != null) {
            List<String> audiences = new ArrayList<>();
            for (String audience : jwt.getAudience()) {
                audiences.add(audience);
            }
            claims.put(OAuth2IntrospectionClaimNames.AUDIENCE, Collections.unmodifiableList(audiences));
        }
        if (jwt.getClaimAsString("client_id") != null) {
            claims.put(OAuth2IntrospectionClaimNames.CLIENT_ID, jwt.getClaimAsString("client_id"));
        }
        if (jwt.getExpiresAt() != null) {
            Instant exp = jwt.getExpiresAt();
            claims.put(OAuth2IntrospectionClaimNames.EXPIRES_AT, exp);
        }
        if (jwt.getIssuedAt() != null) {
            Instant iat = jwt.getIssuedAt();
            claims.put(OAuth2IntrospectionClaimNames.ISSUED_AT, iat);
        }
        if (jwt.getIssuer() != null) {
            claims.put(OAuth2IntrospectionClaimNames.ISSUER, jwt.getIssuer());
        }
        if (jwt.getNotBefore() != null) {
            claims.put(OAuth2IntrospectionClaimNames.NOT_BEFORE, jwt.getNotBefore());
        }
        if (jwt.getClaimAsStringList("scope") != null) {
            List<String> scopes = Collections.unmodifiableList(jwt.getClaimAsStringList("scope"));
            claims.put(OAuth2IntrospectionClaimNames.SCOPE, scopes);

            for (String scope : scopes) {
                authorities.add(new SimpleGrantedAuthority(this.authorityPrefix + scope));
            }
        }
        if (claims.containsKey("authorities")) {
            List<String> authoritiesList = jwt.getClaimAsStringList("authorities");
            for (String authority : authoritiesList)
                authorities.add(new SimpleGrantedAuthority(authority));
        }

        JwtAuthenticationToken result = new JwtAuthenticationToken(jwt,authorities,jwt.getClaimAsString("username"));
        return Mono.just(result);
    }

}
