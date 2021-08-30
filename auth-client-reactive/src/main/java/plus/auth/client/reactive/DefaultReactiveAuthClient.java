package plus.auth.client.reactive;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.reactive.function.client.WebClient;
import plus.auth.entities.AuthClient;
import plus.auth.entities.AuthUser;
import plus.auth.entities.AuthUsers;
import reactor.core.publisher.Mono;

public class DefaultReactiveAuthClient implements ReactiveAuthClient {

    @Getter
    @Setter
    private String apiEndpoint;

    private static final String GET_USER_URI = "/v1/users/{uid}";
    private static final String GET_USERS_URI = "/v1/users?uid={uid}";
    private static final String GET_USER_CLIENT_URI = "/v1/users/{uid}/clients/{cid}";

    private WebClient webClient;

    public DefaultReactiveAuthClient(String clientId,
                                     String clientSecret,
                                     String tokenUri,
                                     String apiEndpoint) {

        this.apiEndpoint = apiEndpoint;
        webClient = WebClient.builder()
                .baseUrl(apiEndpoint)
                .filter(new AuthClientFilter(tokenUri, clientId, clientSecret))
                .build();
    }

    public DefaultReactiveAuthClient(String token, String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
        webClient = WebClient.builder()
                .baseUrl(apiEndpoint)
                .filter(new AuthSimpleClientFilter(token))
                .build();
    }

    @Override
    public Mono<AuthUser> getUser(Long uid) {
        return webClient
                .get()
                .uri(GET_USER_URI, uid)
                .retrieve()
                .bodyToMono(AuthUser.class)
                .onErrorMap(throwable -> new AuthException("Fail to get user: " + uid, throwable))
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
                .uri(GET_USERS_URI, uidString.toString())
                .retrieve().bodyToMono(AuthUsers.class)
                .onErrorMap(throwable -> new AuthException("Fail to get users: " + uidString + ", " + throwable.getMessage(), throwable));
    }

    @Override
    public Mono<AuthClient> getUserClient(Long uid, String cid) {
        return webClient
                .get()
                .uri(GET_USER_CLIENT_URI, uid, cid)
                .retrieve()
                .bodyToMono(AuthClient.class)
                .onErrorMap(throwable -> new AuthException("Fail to get client: " + cid, throwable))
                .switchIfEmpty(Mono.error(new ClientNotFoundException("Client not found: " + cid)));
    }

    @Override
    public Mono<Boolean> isClientMember(Long uid, String cid) {
        return webClient
                .head()
                .uri(GET_USER_CLIENT_URI, uid, cid)
                .exchangeToMono(clientResponse -> Mono.just(clientResponse.statusCode().is2xxSuccessful() ? true : false));
    }
}
