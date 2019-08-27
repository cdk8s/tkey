package com.cdk8s.tkey.server.pojo.bo.cache;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OauthCodeToRedisBO implements Serializable {

	private static final long serialVersionUID = -5698055629424777336L;
	private String tgc;
	private String userInfoRedisKey;
	private String clientId;

	private Long iat;

}
