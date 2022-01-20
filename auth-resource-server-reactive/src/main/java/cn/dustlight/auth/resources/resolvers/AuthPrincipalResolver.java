package cn.dustlight.auth.resources.resolvers;

import cn.dustlight.auth.resources.AuthPrincipalUtil;
import cn.dustlight.auth.resources.core.AuthPrincipal;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class AuthPrincipalResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(AuthPrincipal.class);
    }

    @Override
    public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext bindingContext, ServerWebExchange exchange) {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(securityContext -> {
                    Authentication authentication = securityContext.getAuthentication();
                    if (authentication instanceof AbstractOAuth2TokenAuthenticationToken)
                        return Mono.just(AuthPrincipalUtil.getAuthPrincipal((AbstractOAuth2TokenAuthenticationToken) authentication));
                    return Mono.empty();
                });
    }
}
