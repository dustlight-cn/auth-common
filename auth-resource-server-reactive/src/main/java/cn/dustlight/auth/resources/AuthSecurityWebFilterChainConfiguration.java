package cn.dustlight.auth.resources;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import cn.dustlight.auth.resources.services.AuthJwtAuthenticationConverter;
import cn.dustlight.auth.resources.services.ReactiveAuthOpaqueTokenIntrospector;

@Configuration
public class AuthSecurityWebFilterChainConfiguration {

    protected Log logger = LogFactory.getLog(AuthSecurityWebFilterChainConfiguration.class);

    @Bean
    @ConditionalOnProperty(prefix = "plus.oauth2.resource-server", name = {"token-type"}, havingValue = "jwt", matchIfMissing = true)
    public SecurityWebFilterChain jwtSpringSecurityFilterChain(@Autowired ServerHttpSecurity http,
                                                               @Autowired AuthResourceServerProperties properties,
                                                               @Autowired AuthJwtAuthenticationConverter converter) {
        logger.info("Token Type: Jwt");
        return configure(http)
                .httpBasic().disable()
                .formLogin().disable()
                .cors().and()
                .csrf().disable()
                .oauth2ResourceServer()
                .jwt()
                .jwkSetUri(properties.getJwkSetUri())
                .jwtAuthenticationConverter(converter)
                .and().and().build();
    }

    @Bean
    @ConditionalOnProperty(prefix = "plus.oauth2.resource-server", name = {"token-type"}, havingValue = "opaque")
    public SecurityWebFilterChain opaqueSpringSecurityFilterChain(@Autowired ServerHttpSecurity http,
                                                                  @Autowired AuthResourceServerProperties properties,
                                                                  @Autowired ReactiveAuthOpaqueTokenIntrospector introspector) {
        logger.info("Token Type: Opaque");
        return configure(http)
                .httpBasic().disable()
                .formLogin().disable()
                .cors().and()
                .csrf().disable()
                .oauth2ResourceServer()
                .opaqueToken()
                .introspector(introspector)
                .and().and().build();
    }

    protected ServerHttpSecurity configure(ServerHttpSecurity http){
        return http.authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .anyExchange().authenticated()
                .and();
    }
}
