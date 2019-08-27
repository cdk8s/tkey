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
public class OauthTgcToRedisBO implements Serializable {

	private static final long serialVersionUID = 41535419894343419L;

	private String userInfoRedisKey;
	private Long iat;

	private Boolean boolIsRememberMe;
	private Boolean boolIsMobile;

	private String userAgent;
	private String requestIp;

}
