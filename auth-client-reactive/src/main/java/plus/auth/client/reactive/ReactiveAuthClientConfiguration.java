package plus.auth.client.reactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Configuration
@EnableConfigurationProperties(AuthClientProperties.class)
public class ReactiveAuthClientConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "plus.oauth2.client", value = {"client-secret", "client-id", "api-endpoint"})
    public ReactiveAuthClient reactiveAuthClient(@Autowired AuthClientProperties authClientProperties) {
        return new DefaultReactiveAuthClient(ClientRegistration.withRegistrationId("auth")
                .clientId(authClientProperties.getClientId())
                .clientSecret(authClientProperties.getClientSecret())
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .tokenUri(authClientProperties.getTokenUri())
                .build(),
                authClientProperties.getApiEndpoint());
    }

}
