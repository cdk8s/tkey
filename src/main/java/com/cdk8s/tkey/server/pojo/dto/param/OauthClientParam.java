package com.cdk8s.tkey.server.pojo.dto.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class OauthClientParam {

	private String clientId;
	private String clientSecret;

}
