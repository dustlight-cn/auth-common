package cn.dustlight.auth.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AuthUserRole {

    String rid,roleName,roleDescription;

    boolean expired;

    Date createdAt,updatedAt,expiredAt;
}
