package plus.auth.resources;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import plus.auth.resources.core.AuthPrincipal;
import plus.auth.resources.core.DefaultAuthPrincipal;

import java.util.Collection;
import java.util.Map;

public class AuthPrincipalUtil {

    public static AuthPrincipal getAuthPrincipal(AbstractOAuth2TokenAuthenticationToken authentication) {
        if (authentication == null)
            return null;
        DefaultAuthPrincipal tmp = new DefaultAuthPrincipal();
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
            tmp.setBody(authentication.getTokenAttributes());
        }
        return tmp;
    }
}
