package plus.auth.resources;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import plus.auth.resources.core.AuthPrincipal;
import plus.auth.resources.core.DefaultAuthPrincipal;

import java.util.Collection;

public class AuthPrincipalUtil {

    public static AuthPrincipal getAuthPrincipal(AbstractOAuth2TokenAuthenticationToken authentication) {
        AbstractOAuth2TokenAuthenticationToken a = authentication;
        DefaultAuthPrincipal tmp = new DefaultAuthPrincipal();
        tmp.setAuthorities(AuthorityUtils.authorityListToSet(a.getAuthorities()));
        tmp.setUid(Long.valueOf(a.getName()));
        if (a.getTokenAttributes() != null) {
            tmp.setClientId((String) a.getTokenAttributes().get("client_id"));
            tmp.setClientAuthorities((Collection<String>) a.getTokenAttributes().get("client_authorities"));
            tmp.setName((String) a.getTokenAttributes().get("user_name"));
            tmp.setScope((Collection<String>) a.getTokenAttributes().get("scope"));
            tmp.setBody(a.getTokenAttributes());
        }
        return tmp;
    }
}
