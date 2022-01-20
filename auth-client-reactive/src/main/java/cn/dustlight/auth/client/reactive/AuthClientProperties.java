package cn.dustlight.auth.client.reactive;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "plus.oauth2.client")
public class AuthClientProperties {

    private String clientId,clientSecret;
    private String tokenUri = "http://api.wgv/v1/oauth/token";
    private String apiEndpoint = "http://api.wgv";
}
