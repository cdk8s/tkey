package com.cdk8s.tkey.server.util.response;

/**
 * 微信：https://mp.weixin.qq.com/wiki?action=doc&id=mp1433747234
 * 钉钉：https://open-doc.dingtalk.com/microapp/faquestions/rftpfg
 */
public enum ResponseErrorEnum {

	SYSTEM_BUSY(1, "系统繁忙，请稍候重试"),
	ILLEGAL_STATE(100001, "非法访问"),
	PARAM_REQUIRED(100002, "参数不能为空"),
	PARAM_FORMAT_ILLEGAL(100003, "参数格式错误"),
	REQUEST_DATA_DUPLICATION(100004, "重复请求"),
	REQUEST_DATA_ERROR(100005, "请求数据错误"),
	REQUEST_DATA_NOT_MATCH(100006, "请求数据不一致"),
	RECORD_NOT_EXIST(100007, "数据不存在"),
	RECORD_EXISTED(100008, "数据已存在"),
	RECORD_ILLEGAL_STATE(100009, "数据异常"),
	CALL_INNER_ERROR(100010, "调用内部服务接口异常"),
	THIRD_PART_ERROR(100011, "调用第三方接口异常"),
	SYSTEM_ERROR(999999, "系统异常");

	private int code;
	private String description;

	ResponseErrorEnum(final int code, final String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

}
