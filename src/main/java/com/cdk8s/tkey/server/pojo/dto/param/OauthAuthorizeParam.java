package com.cdk8s.tkey.server.pojo.dto.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class OauthAuthorizeParam {
	private String responseType;
	private String clientId;
	private String redirectUri;
	private String state;
}
