package com.cdk8s.tkey.server.pojo.bo.cache;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Setter
@Getter
@ToString
public class OauthClientToRedisBO implements Serializable {

	private static final long serialVersionUID = 5004734902174453355L;

	private Long id;
	private String clientName;
	private String clientId;
	private String clientSecret;
	private String clientUrl;
	private String clientDesc;
	private String logoUrl;

}
