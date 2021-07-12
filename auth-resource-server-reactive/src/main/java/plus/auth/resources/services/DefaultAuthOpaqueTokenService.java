package plus.auth.resources.services;

import lombok.Getter;
import lombok.Setter;
import plus.auth.resources.core.AbstractOpaqueAuthTokenService;
import plus.auth.resources.core.AuthPrincipal;
import plus.auth.resources.core.CheckTokenException;
import plus.auth.resources.core.DefaultAuthPrincipal;

import java.time.Instant;
import java.util.Collection;

public class DefaultAuthOpaqueTokenService extends AbstractOpaqueAuthTokenService<DefaultAuthOpaqueTokenService.DefaultBody> {

    public DefaultAuthOpaqueTokenService(String clientId, String clientSecret, String uri) {
        super(clientId, clientSecret, uri);
    }

    @Override
    protected Class<DefaultBody> bodyClass() {
        return DefaultBody.class;
    }

    @Override
    protected AuthPrincipal map(DefaultBody body) {
        if (body.exp != null && body.exp.isBefore(Instant.now()))
            throw new CheckTokenException("Token expired!");
        return new DefaultAuthPrincipal(body.authorities,
                body.scope,
                body.client_authorities,
                body.user_name,
                body.client_id,
                Long.valueOf(body.username),
                body);
    }

    @Getter
    @Setter
    public static class DefaultBody {

        private String client_id, username, user_name;
        private Collection<String> authorities, scope;
        private Collection<String> client_authorities;
        private Boolean active;
        private Instant exp;
    }
}
