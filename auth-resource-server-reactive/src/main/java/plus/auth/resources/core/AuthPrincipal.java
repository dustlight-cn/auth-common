package plus.auth.resources.core;

import java.security.Principal;
import java.util.Collection;

public interface AuthPrincipal extends Principal {

    Collection<String> getAuthority();

    Collection<String> getScope();

    String getClientId();

    Long getUid();

    Object getBody();

}
