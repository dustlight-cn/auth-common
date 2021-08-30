package plus.auth.resources.core;

import java.util.Collection;

public interface AuthPrincipal {

    Collection<String> getAuthorities();

    Collection<String> getClientAuthorities();

    Collection<String> getScope();

    String getClientId();

    String getName();

    Long getUid();

    Object getBody();

    String getUidString();

    Boolean isMember();
}
