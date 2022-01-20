package cn.dustlight.auth.resources;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "dustlight.auth.oauth2.resource-server")
public class AuthResourceServerProperties {

    private String clientId, clientSecret,
            uri = "https://api.dustlight.cn/v1/token/validity",
            jwkSetUri = "https://api.dustlight.cn/v1/jwk",
            scopePrefix = "SCOPE_";
    private TokenType tokenType = TokenType.JWT;

    public enum TokenType {
        JWT,
        OPAQUE,
        NONE
    }

}
