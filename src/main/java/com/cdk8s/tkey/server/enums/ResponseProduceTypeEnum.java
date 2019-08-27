package com.cdk8s.tkey.server.enums;

/**
 * 返回结果类型：JSON / HTML
 */
public enum ResponseProduceTypeEnum implements BasicEnum {

	JSON(1, "application/json;charset=UTF-8"),
	HTML(2, "text/html");

	private int code;
	private String description;

	ResponseProduceTypeEnum(final int code, final String description) {
		this.code = code;
		this.description = description;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
