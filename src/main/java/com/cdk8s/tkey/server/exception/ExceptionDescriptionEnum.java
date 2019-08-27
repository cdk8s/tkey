package com.cdk8s.tkey.server.exception;

public enum ExceptionDescriptionEnum {

	CANNOT_BE_CAST_TO("cannot be cast to", "系统转换对象异常，请联系管理员进行处理"),
	REDIS_CONNECTION_EXCEPTION("RedisConnectionException", "连接缓存失败，请联系管理员进行处理"),
	ERROR_QUERYING_DATABASE("Error querying database", "查询数据库失败，请联系管理员进行处理"),
	REQUEST_CONTENT_TYPE_NOT_SUPPORTED("Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported", "请求内容类型不支持"),
	UNRECOGNIZED_PROPERTY_EXCEPTION("com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException", "JSON 序列化异常，有无法识别的属性"),
	NO_HANDLER_FOUND("No handler found for", "请求地址不存在"),
	JSON_PAUSE_ERROR("JSON parse error", "JSON 转换失败"),
	SQL_DEFAULT_VALUE("doesn't have a default value", "缺少指定字段值"),
	SQL_QUERY_ERROR("Error querying database", "查询数据库失败"),
	INPUT_FORMAT_ERROR("For input string:", "输入格式有误"),
	DATA_SIZE_TOO_LONG("java.sql.SQLException: Data too long for column", "存在某个字段过长"),
	DATABASE_UPDATE_ERROR("Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException", "操作数据失败，违背完整性约束"),
	SERVICE_REFUSE("java.net.ConnectException: Connection refused (Connection refused)", "连接某些服务被拒接"),
	SERVICE_NOT_AVAILABLE("com.netflix.client.ClientException: Load balancer does not have available server for client", "存在某些服务不可用"),
	SERVICE_TIMEOUT("java.util.concurrent.TimeoutException", "10000服务连接超时"),
	MYSQL_CONNECTION_TIMEOUT("Could not open JDBC Connection for transaction; nested exception is com.mysql.jdbc", "JDBC 服务连接超时"),
	REDIS_CONNECTION_TIMEOUT(" redis.clients.jedis.exceptions.JedisConnectionException", "Redis 服务连接超时"),
	RABBITMQ_CONNECTION_TIMEOUT("org.springframework.amqp.AmqpTimeoutException: java.util.concurrent.TimeoutException", "MQ 服务连接超时"),
	FEIGN_FAIL_AND_NO_FALLBACK("failed and no fallback available", "Feign 连接失败且无回退方法"),
	FEIGN_FAIL_AND_FALLBACK_AVAILABLE("failed and fallback failed", "Feign 连接失败且回退失败");

	private String keyWord;
	private String description;

	public String getKeyWord() {
		return keyWord;
	}

	public String getDescription() {
		return description;
	}

	ExceptionDescriptionEnum(final String keyWord, final String description) {
		this.keyWord = keyWord;
		this.description = description;
	}

}
