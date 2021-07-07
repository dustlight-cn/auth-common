package plus.auth.resources;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "plus.oauth2.resource-server")
public class AuthResourceServerProperties {

    private String clientId, clientSecret, uri, jwkSetUri, scopePrefix = "SCOPE_";
    private TokenType tokenType = TokenType.JWT;

    public enum TokenType {
        JWT,
        OPAQUE,
        NONE
    }

}
