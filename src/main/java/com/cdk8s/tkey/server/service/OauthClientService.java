package com.cdk8s.tkey.server.service;

import com.cdk8s.tkey.server.constant.GlobalVariable;
import com.cdk8s.tkey.server.exception.OauthApiException;
import com.cdk8s.tkey.server.pojo.bo.cache.OauthClientToRedisBO;
import com.cdk8s.tkey.server.util.JsonUtil;
import com.cdk8s.tkey.server.util.StringUtil;
import com.cdk8s.tkey.server.util.redis.StringRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class OauthClientService {

	@Autowired
	private StringRedisService<String, String> stringRedisService;

	//=====================================业务处理 start=====================================

	public OauthClientToRedisBO findByClientId(String clientId) {
		String clientIdRedisKey = GlobalVariable.REDIS_CLIENT_ID_PREFIX + clientId;
		String result = stringRedisService.get(clientIdRedisKey);
		if (StringUtil.isBlank(result)) {
			throw new OauthApiException("client_id 不存在");
		}
		return JsonUtil.toObject(result, OauthClientToRedisBO.class);
	}

	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================

	//=====================================私有方法  end=====================================

}
