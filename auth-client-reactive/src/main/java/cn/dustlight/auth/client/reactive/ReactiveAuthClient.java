package cn.dustlight.auth.client.reactive;

import io.swagger.v3.oas.annotations.Hidden;
import cn.dustlight.auth.entities.AuthClient;
import cn.dustlight.auth.entities.AuthUser;
import cn.dustlight.auth.entities.AuthUsers;
import reactor.core.publisher.Mono;

@Hidden
public interface ReactiveAuthClient {

    Mono<AuthUser> getUser(Long uid);

    Mono<AuthUsers> getUsers(Long... uids);

    Mono<AuthClient> getUserClient(Long uid, String cid);

    Mono<Boolean> isClientMember(Long uid, String cid);
}
