package com.cdk8s.tkey.server.strategy;


import com.cdk8s.tkey.server.pojo.bo.handle.OauthTokenStrategyHandleBO;
import com.cdk8s.tkey.server.pojo.dto.OauthToken;
import com.cdk8s.tkey.server.pojo.dto.param.OauthTokenParam;

public interface OauthTokenStrategyInterface {

	/**
	 * 检查请求参数
	 */
	void checkParam(OauthTokenParam oauthTokenParam, OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO);

	/**
	 * 生成 Token
	 */
	OauthToken handle(OauthTokenParam oauthTokenParam, OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO);

}
