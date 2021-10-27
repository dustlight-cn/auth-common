package plus.auth.resources;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.util.StringUtils;
import plus.auth.client.reactive.ReactiveAuthClient;
import plus.auth.resources.core.AuthPrincipal;
import plus.auth.resources.core.DefaultAuthPrincipal;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;

public class AuthPrincipalUtil {

    public static AuthPrincipal getAuthPrincipal(AbstractOAuth2TokenAuthenticationToken authentication) {
        if (authentication == null)
            return null;
        DefaultAuthPrincipal tmp = new DefaultAuthPrincipal();
        if (authentication.getToken() != null)
            tmp.setAccessToken(authentication.getToken().getTokenValue());
        if (authentication.getAuthorities() != null)
            tmp.setAuthorities(AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        if (authentication.getName() != null)
            tmp.setUid(Long.valueOf(authentication.getName()));
        if (authentication.getTokenAttributes() != null) {
            Map tokenAttributes = authentication.getTokenAttributes();
            if (tokenAttributes.containsKey("client_id"))
                tmp.setClientId(tokenAttributes.get("client_id").toString());
            if (tokenAttributes.containsKey("client_authorities"))
                tmp.setClientAuthorities((Collection<String>) tokenAttributes.get("client_authorities"));
            if (tokenAttributes.containsKey("user_name"))
                tmp.setName(tokenAttributes.get("user_name").toString());
            if (tokenAttributes.containsKey("scope"))
                tmp.setScope((Collection<String>) tokenAttributes.get("scope"));
            if (tokenAttributes.containsKey("member"))
                tmp.setMember((Boolean) tokenAttributes.get("member"));
            tmp.setBody(authentication.getTokenAttributes());
        }
        return tmp;
    }

    public static Mono<String> obtainClientId(ReactiveAuthClient client, String clientId, AuthPrincipal principal) {
        if (StringUtils.hasText(clientId) && !client.equals(principal.getClientId()))
            return client.isClientMember(principal.getUid(), clientId)
                    .flatMap(aBoolean -> aBoolean ? Mono.just(clientId) : Mono.error(new AccessDeniedException("Access Denied")));
        return Mono.just(principal.getClientId());
    }

    public static Mono<String> obtainClientIdRequireMember(ReactiveAuthClient client, String clientId, AuthPrincipal principal) {
        if (StringUtils.hasText(clientId) && !client.equals(principal.getClientId()))
            return client.isClientMember(principal.getUid(), clientId)
                    .flatMap(aBoolean -> aBoolean ? Mono.just(clientId) : Mono.error(new AccessDeniedException("Access Denied")));
        return principal.isMember() || principal.getUid() == null ? Mono.just(principal.getClientId()) : Mono.error(new AccessDeniedException("Access Denied"));
    }
}
