package com.cdk8s.tkey.server.pojo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OauthToken implements Serializable {

	private static final long serialVersionUID = 7975415790497139511L;

	private String accessToken;
	private String tokenType;
	private Integer expiresIn;

	private String refreshToken;

}
