package cn.dustlight.auth.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import cn.dustlight.auth.client.reactive.AuthClientProperties;
import cn.dustlight.auth.resources.resolvers.AuthPrincipalResolver;
import cn.dustlight.auth.resources.resolvers.SimpleReactiveAuthClientResolver;
import cn.dustlight.auth.resources.services.AuthJwtAuthenticationConverter;
import cn.dustlight.auth.resources.services.DefaultAuthJwtTokenService;
import cn.dustlight.auth.resources.services.ReactiveAuthOpaqueTokenIntrospector;
import cn.dustlight.auth.resources.services.DefaultAuthOpaqueTokenService;

@Configuration
@EnableConfigurationProperties(AuthResourceServerProperties.class)
public class AuthResourceServerConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "dustlight.auth.oauth2.resource-server", name = {"token-type"}, havingValue = "opaque")
    public DefaultAuthOpaqueTokenService defaultAuthOpaqueTokenService(@Autowired AuthResourceServerProperties properties) {
        return new DefaultAuthOpaqueTokenService(properties.getClientId(), properties.getClientSecret(), properties.getUri());
    }

    @Bean
    @ConditionalOnProperty(prefix = "dustlight.auth.oauth2.resource-server", name = {"token-type"}, havingValue = "jwt", matchIfMissing = true)
    public DefaultAuthJwtTokenService defaultAuthJwtTokenService(@Autowired AuthResourceServerProperties properties) {
        return new DefaultAuthJwtTokenService(properties.getJwkSetUri());
    }

    @Bean
    @ConditionalOnProperty(prefix = "dustlight.auth.oauth2.resource-server", name = {"client-id", "client-secret"})
    public ReactiveAuthOpaqueTokenIntrospector reactiveAuthOpaqueTokenIntrospector(@Autowired AuthResourceServerProperties properties) {
        return new ReactiveAuthOpaqueTokenIntrospector(properties.getUri(), properties.getClientId(), properties.getClientSecret());
    }

    @Bean
    public AuthPrincipalResolver authPrincipalResolver() {
        return new AuthPrincipalResolver();
    }

    @Bean
    @ConditionalOnProperty(prefix = "dustlight.auth.oauth2.client", name = {"client-id", "client-secret"})
    public SimpleReactiveAuthClientResolver simpleReactiveAuthClientResolver(@Autowired AuthClientProperties properties) {
        return new SimpleReactiveAuthClientResolver(properties.getApiEndpoint());
    }

    @Bean
    public AuthJwtAuthenticationConverter authJwtAuthenticationConverter(@Autowired AuthResourceServerProperties properties) {
        AuthJwtAuthenticationConverter converter = new AuthJwtAuthenticationConverter();
        converter.setAuthorityPrefix(properties.getScopePrefix());
        return converter;
    }

}
