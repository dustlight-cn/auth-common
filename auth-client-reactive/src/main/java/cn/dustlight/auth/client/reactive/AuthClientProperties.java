package cn.dustlight.auth.client.reactive;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "dustlight.auth.oauth2.client")
public class AuthClientProperties {

    private String clientId,clientSecret;
    private String tokenUri = "https://api.dustlight.cn/v1/oauth/token";
    private String apiEndpoint = "https://api.dustlight.cn";
}
