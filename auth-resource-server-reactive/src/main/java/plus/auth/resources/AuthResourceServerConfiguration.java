package plus.auth.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import plus.auth.resources.services.AuthJwtAuthenticationConverter;
import plus.auth.resources.services.ReactiveAuthOpaqueTokenIntrospector;
import plus.auth.resources.services.DefaultAuthTokenService;

@Configuration
@EnableConfigurationProperties(AuthResourceServerProperties.class)
public class AuthResourceServerConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "plus.oauth2.resource-server",
            name = {"client-id", "client-secret", "uri"})
    public DefaultAuthTokenService defaultOAuth2TokenService(@Autowired AuthResourceServerProperties properties) {
        return new DefaultAuthTokenService(properties.getClientId(), properties.getClientSecret(), properties.getUri());
    }

    @Bean
    @ConditionalOnProperty(prefix = "plus.oauth2.resource-server",name = {"client-id", "client-secret", "uri"})
    public ReactiveAuthOpaqueTokenIntrospector reactiveAuthOpaqueTokenIntrospector(@Autowired AuthResourceServerProperties properties) {
        return new ReactiveAuthOpaqueTokenIntrospector(properties.getUri(), properties.getClientId(), properties.getClientSecret());
    }

    @Bean
    public AuthJwtAuthenticationConverter authJwtAuthenticationConverter(@Autowired AuthResourceServerProperties properties) {
        AuthJwtAuthenticationConverter converter = new AuthJwtAuthenticationConverter();
        converter.setAuthorityPrefix(properties.getScopePrefix());
        return converter;
    }

}
