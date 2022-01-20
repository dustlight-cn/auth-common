package cn.dustlight.auth.client.reactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AuthClientProperties.class)
public class ReactiveAuthClientConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "dustlight.auth.oauth2.client", name = {"client-secret", "client-id", "api-endpoint"})
    public ReactiveAuthClient reactiveAuthClient(@Autowired AuthClientProperties properties) {
        return new DefaultReactiveAuthClient(properties.getClientId(),
                properties.getClientSecret(),
                properties.getTokenUri(),
                properties.getApiEndpoint());
    }

}
