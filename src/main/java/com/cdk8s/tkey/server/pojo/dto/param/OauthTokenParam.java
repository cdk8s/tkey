package com.cdk8s.tkey.server.pojo.dto.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class OauthTokenParam extends OauthClientParam {
	private String grantType;

	private String code;
	private String refreshToken;
	private String redirectUri;

	private String username;
	private String password;
}
