package com.cdk8s.tkey.server.exception;


import com.cdk8s.tkey.server.util.response.ResponseErrorEnum;

public class SystemException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String message;
	private int code = 500;

	public SystemException(String message) {
		super(message);
		this.message = message;
	}

	public SystemException(String message, Throwable e) {
		super(message, e);
		this.message = message;
	}

	public SystemException(String message, ResponseErrorEnum responseErrorEnum) {
		super(message);
		this.message = message;
		this.code = responseErrorEnum.getCode();
	}

	public SystemException(String message, ResponseErrorEnum responseErrorEnum, Throwable e) {
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
}
