package plus.auth.resources.core;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DefaultAuthPrincipal implements AuthPrincipal {

    private Collection<String> authority, scope;
    private String name, clientId;
    private Long uid;
    private Object body;

}
