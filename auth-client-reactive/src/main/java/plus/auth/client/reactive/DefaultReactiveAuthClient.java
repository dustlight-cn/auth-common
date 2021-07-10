package plus.auth.client.reactive;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.AuthenticatedPrincipalServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;
import plus.auth.entities.AuthUser;
import plus.auth.entities.AuthUsers;
import reactor.core.publisher.Mono;

public class DefaultReactiveAuthClient implements ReactiveAuthClient {

    @Getter
    @Setter
    private String apiEndpoint;

    private static final String GET_USER_URI = "/v1/users/{uid}";
    private static final String GET_USERS_URI = "/v1/users?uid={uid}";

    private WebClient webClient;

    public DefaultReactiveAuthClient(ClientRegistration clientRegistration,
                                     String apiEndpoint) {
        ReactiveClientRegistrationRepository cr = s -> Mono.just(clientRegistration);
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(cr,
                        new AuthenticatedPrincipalServerOAuth2AuthorizedClientRepository(new InMemoryReactiveOAuth2AuthorizedClientService(cr)));
        this.apiEndpoint = apiEndpoint;
        oauth.setDefaultClientRegistrationId("auth");
        webClient = WebClient.builder()
                .filter(oauth)
                .build();
    }

    @Override
    public Mono<AuthUser> getUser(Long uid) {
        return webClient
                .get()
                .uri(apiEndpoint + GET_USER_URI, uid)
                .retrieve()
                .bodyToMono(AuthUser.class)
                .onErrorMap(throwable -> new AuthException("Fail to get user: " + uid,throwable))
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found: " + uid)));
    }

    @Override
    public Mono<AuthUsers> getUsers(Long... uids) {
        StringBuilder uidString = new StringBuilder();
        if (uids != null) {
            for (Long u : uids) {
                if (uidString.length() > 0)
                    uidString.append(',');
                uidString.append(u);
            }
        }
        return webClient
                .get()
                .uri(apiEndpoint + GET_USERS_URI, uidString.toString())
                .retrieve().bodyToMono(AuthUsers.class)
                .onErrorMap(throwable -> new AuthException("Fail to get users: " + uidString + ", " + throwable.getMessage(),throwable));
    }
}
