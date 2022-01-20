package cn.dustlight.auth.resources.core;

import io.swagger.v3.oas.annotations.Hidden;

import java.util.Collection;

@Hidden
public interface AuthPrincipal {

    Collection<String> getAuthorities();

    Collection<String> getClientAuthorities();

    Collection<String> getScope();

    String getClientId();

    String getName();

    Long getUid();

    Object getBody();

    String getUidString();

    Boolean isMember();

    String getAccessToken();
}
