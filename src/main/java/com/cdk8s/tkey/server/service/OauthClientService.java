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

	/**
	 * 这里必须采用 value 为 String 类型，不然 客户端和服务端都要读取该信息，但是又没有共同的类进行序列化，所以必须转换成 JSON 字符串
	 * 如果后面出现的公共类越来越多我再考虑独立出一个 jar 包出来维护
	 */
	@Autowired
	private StringRedisService<String, String> clientRedisService;

	//=====================================业务处理 start=====================================

	public OauthClientToRedisBO findByClientId(String clientId) {
		String clientIdRedisKey = GlobalVariable.REDIS_CLIENT_ID_KEY_PREFIX + clientId;
		String result = clientRedisService.get(clientIdRedisKey);
		if (StringUtil.isBlank(result)) {
			throw new OauthApiException("client_id 不存在");
		}
		return JsonUtil.toObject(result, OauthClientToRedisBO.class);
	}

	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================

	//=====================================私有方法  end=====================================

}
