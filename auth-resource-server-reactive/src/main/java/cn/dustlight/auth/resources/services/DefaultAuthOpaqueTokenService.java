package cn.dustlight.auth.resources.services;

import cn.dustlight.auth.resources.core.AbstractOpaqueAuthTokenService;
import cn.dustlight.auth.resources.core.AuthPrincipal;
import cn.dustlight.auth.resources.core.CheckTokenException;
import cn.dustlight.auth.resources.core.DefaultAuthPrincipal;
import lombok.Getter;
import lombok.Setter;

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
    protected AuthPrincipal map(DefaultBody body, String token) {
        if (body.exp != null && body.exp.isBefore(Instant.now()))
            throw new CheckTokenException("Token expired!");
        return new DefaultAuthPrincipal(body.authorities,
                body.scope,
                body.client_authorities,
                body.user_name,
                body.client_id,
                body.member,
                Long.valueOf(body.username),
                body,
                token);
    }

    @Getter
    @Setter
    public static class DefaultBody {

        private String client_id, username, user_name;
        private Collection<String> authorities, scope;
        private Collection<String> client_authorities;
        private Boolean member;
        private Boolean active;
        private Instant exp;
    }
}
