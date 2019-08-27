package com.cdk8s.tkey.server.pojo.bo.cache;

import com.cdk8s.tkey.server.pojo.dto.OauthUserAttribute;
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
public class OauthUserInfoToRedisBO implements Serializable {


	private static final long serialVersionUID = 970124664158178650L;
	private OauthUserAttribute userAttribute;

	private Long iat;

}
