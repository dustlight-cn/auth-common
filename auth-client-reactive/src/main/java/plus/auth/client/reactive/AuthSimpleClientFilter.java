package plus.auth.client.reactive;

import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

public class AuthSimpleClientFilter implements ExchangeFilterFunction {

    private String token;

    public AuthSimpleClientFilter(String token) {
        this.token = token;
    }

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        return next.exchange(ClientRequest.from(request)
                .header("Authorization", String.format("Bearer %s", token))
                .build());
    }
}
