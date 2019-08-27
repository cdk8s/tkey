package com.cdk8s.tkey.server.exception;

import com.cdk8s.tkey.server.enums.ResponseProduceTypeEnum;
import com.cdk8s.tkey.server.util.response.ResponseErrorEnum;

public class OauthApiException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String message;
	private int code = 500;
	private String pagePath;
	private ResponseProduceTypeEnum responseProduceTypeEnum;

	public OauthApiException(String message) {
		super(message);
		this.message = message;
	}

	public OauthApiException(String message, Throwable e) {
		super(message, e);
		this.message = message;
	}

	public OauthApiException(String message, ResponseErrorEnum responseErrorEnum) {
		super(message);
		this.message = message;
		this.code = responseErrorEnum.getCode();
	}

	public OauthApiException(String message, ResponseProduceTypeEnum responseProduceTypeEnum) {
		super(message);
		this.message = message;
		this.responseProduceTypeEnum = responseProduceTypeEnum;
	}

	public OauthApiException(String message, ResponseErrorEnum responseErrorEnum, ResponseProduceTypeEnum responseProduceTypeEnum) {
		super(message);
		this.message = message;
		this.responseProduceTypeEnum = responseProduceTypeEnum;
		this.code = responseErrorEnum.getCode();
	}

	public OauthApiException(String message, ResponseProduceTypeEnum responseProduceTypeEnum, String pagePath) {
		super(message);
		this.message = message;
		this.pagePath = pagePath;
		this.responseProduceTypeEnum = responseProduceTypeEnum;
	}

	public OauthApiException(String message, ResponseErrorEnum responseErrorEnum, ResponseProduceTypeEnum responseProduceTypeEnum, String pagePath) {
		super(message);
		this.message = message;
		this.pagePath = pagePath;
		this.responseProduceTypeEnum = responseProduceTypeEnum;
		this.code = responseErrorEnum.getCode();
	}

	public OauthApiException(String message, ResponseErrorEnum responseErrorEnum, Throwable e) {
		super(message, e);
		this.message = message;
		this.code = responseErrorEnum.getCode();
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public ResponseProduceTypeEnum getResponseProduceTypeEnum() {
		return responseProduceTypeEnum;
	}

	public void setResponseProduceTypeEnum(ResponseProduceTypeEnum responseProduceTypeEnum) {
		this.responseProduceTypeEnum = responseProduceTypeEnum;
	}

	public String getPagePath() {
		return pagePath;
	}

	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}
}
