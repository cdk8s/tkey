package com.cdk8s.tkey.server.util.okhttp;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OkHttpResponse {

	private int status;
	private String response;
}
