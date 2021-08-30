package plus.auth.resources.resolvers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import plus.auth.client.reactive.DefaultReactiveAuthClient;
import plus.auth.client.reactive.ReactiveAuthClient;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Getter
@Setter
public class SimpleReactiveAuthClientResolver implements HandlerMethodArgumentResolver {

    private String apiEndpoint;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(ReactiveAuthClient.class);
    }

    @Override
    public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext bindingContext, ServerWebExchange exchange) {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(securityContext -> {
                    Authentication authentication = securityContext.getAuthentication();
                    if (authentication instanceof AbstractOAuth2TokenAuthenticationToken)
                        return Mono.just(new DefaultReactiveAuthClient(((AbstractOAuth2TokenAuthenticationToken<?>) authentication).getToken().getTokenValue(),
                                apiEndpoint));
                    return Mono.empty();
                });
    }
}
