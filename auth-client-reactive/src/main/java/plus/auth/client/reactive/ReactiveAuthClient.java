package plus.auth.client.reactive;

import plus.auth.entities.AuthClient;
import plus.auth.entities.AuthUser;
import plus.auth.entities.AuthUsers;
import reactor.core.publisher.Mono;

public interface ReactiveAuthClient {

    Mono<AuthUser> getUser(Long uid);

    Mono<AuthUsers> getUsers(Long... uids);

    Mono<AuthClient> getUserClient(Long uid, String cid);

    Mono<Boolean> isClientMember(Long uid, String cid);
}
