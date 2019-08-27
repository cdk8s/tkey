package com.cdk8s.tkey.server.pojo.dto.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OauthIntrospectTokenParam extends OauthClientParam {

	private String token;
	private String tokenTypeHint;

}
