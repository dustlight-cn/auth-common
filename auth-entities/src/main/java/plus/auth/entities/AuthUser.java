package plus.auth.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;

@Getter
@Setter
public class AuthUser {

    @JsonSerialize(using = ToStringSerializer.class)
    Long uid;

    String email;

    String phone;

    String username;

    String nickname;

    String gender;

    String avatar;

    Date accountExpiredAt,credentialsExpiredAt,unlockAt,createdAt,updatedAt;

    Collection<AuthUserRole> userRoles;

    Collection<String> authorities;
}
