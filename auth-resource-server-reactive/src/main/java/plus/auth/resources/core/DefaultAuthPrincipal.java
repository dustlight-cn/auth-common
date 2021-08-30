package plus.auth.resources.core;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DefaultAuthPrincipal implements AuthPrincipal {

    private Collection<String> authorities, scope;
    private Collection<String> clientAuthorities;
    private String name, clientId;
    private Boolean member;
    private Long uid;
    private Object body;

    @Override
    public String getUidString() {
        return uid != null ? uid.toString() : null;
    }

    @Override
    public Boolean isMember() {
        return member;
    }
}
