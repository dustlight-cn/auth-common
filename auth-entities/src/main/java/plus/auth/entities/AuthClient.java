package plus.auth.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthClient {

    @JsonSerialize(using = ToStringSerializer.class)
    Long uid;

    String cid,name,description,logo,secret;

    Integer status;
}
