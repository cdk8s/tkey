package com.cdk8s.tkey.server.pojo.dto.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class OauthFormLoginParam extends OauthAuthorizeParam {
	private String username;
	private String password;
	private Boolean boolIsRememberMe;

}
