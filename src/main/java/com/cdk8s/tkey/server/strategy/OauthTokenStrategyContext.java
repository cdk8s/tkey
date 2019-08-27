package com.cdk8s.tkey.server.strategy;

import com.cdk8s.tkey.server.pojo.bo.handle.OauthTokenStrategyHandleBO;
import com.cdk8s.tkey.server.pojo.dto.OauthToken;
import com.cdk8s.tkey.server.pojo.dto.param.OauthTokenParam;
import com.cdk8s.tkey.server.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class OauthTokenStrategyContext {


	private final Map<String, OauthTokenStrategyInterface> strategyMap = new ConcurrentHashMap<>();

	@Autowired
	public OauthTokenStrategyContext(Map<String, OauthTokenStrategyInterface> strategyMap) {
		this.strategyMap.clear();
		strategyMap.forEach(this.strategyMap::put);
	}

	public void checkParam(String beanName, OauthTokenParam oauthTokenParam, OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO) {
		if (StringUtil.isNotBlank(beanName)) {
			strategyMap.get(beanName).checkParam(oauthTokenParam, oauthTokenStrategyHandleBO);
		}
	}

	public OauthToken generateOauthTokenInfo(String beanName, OauthTokenParam oauthTokenParam, OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO) {
		if (StringUtil.isNotBlank(beanName)) {
			return strategyMap.get(beanName).handle(oauthTokenParam, oauthTokenStrategyHandleBO);
		}
		return null;
	}


}
