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
public class OauthUserAttribute implements Serializable {

	private static final long serialVersionUID = -5890673731362415002L;

	private String email;
	private String userId;
	private String username;

}
