package cn.dustlight.auth.entities;

import cn.dustlight.auth.utils.ToStringCollectionSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class AuthClient {

    @JsonSerialize(using = ToStringSerializer.class)
    Long uid;

    String cid, name, description, logo, secret;

    @JsonSerialize(using = ToStringCollectionSerializer.class)
    Collection<Long> members;

    Integer status;
}
