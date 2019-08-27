package com.cdk8s.tkey.server.service;


import com.cdk8s.tkey.server.constant.GlobalVariable;
import com.cdk8s.tkey.server.pojo.dto.OauthToken;
import com.cdk8s.tkey.server.properties.OauthProperties;
import com.cdk8s.tkey.server.util.NumericGeneratorUtil;
import com.cdk8s.tkey.server.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class OauthGenerateService {

	@Autowired
	private OauthProperties oauthProperties;

	//=====================================业务处理 start=====================================

	public OauthToken generateOauthTokenInfoBO(boolean needIncludeRefreshToken) {
		OauthToken oauthToken = new OauthToken();
		oauthToken.setAccessToken(generateAccessToken());
		if (needIncludeRefreshToken) {
			oauthToken.setRefreshToken(generateRefreshToken());
		}
		oauthToken.setTokenType(GlobalVariable.OAUTH_TOKEN_TYPE);
		oauthToken.setExpiresIn(oauthProperties.getAccessTokenMaxTimeToLiveInSeconds());

		return oauthToken;
	}

	public String generateUserInfoRedisKey(String userId) {
		return GlobalVariable.OAUTH_USER_INFO_REDIS_KEY_PREFIX + userId;
	}

	public String generateTgc() {
		return getUniqueTicket(GlobalVariable.OAUTH_TGC_PREFIX);
	}

	public String generateCode() {
		return getUniqueTicket(GlobalVariable.OAUTH_CODE_PREFIX);
	}

	public String generateAccessToken() {
		return getUniqueTicket(GlobalVariable.OAUTH_ACCESS_TOKEN_PREFIX);
	}

	public String generateRefreshToken() {
		return getUniqueTicket(GlobalVariable.OAUTH_REFRESH_TOKEN_PREFIX);
	}

	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================

	private String getUniqueTicket(String prefix) {
		// 组成结构：前缀-节点编号+计算器数-随机数
		return prefix + oauthProperties.getNodeNumber() + NumericGeneratorUtil.getNumber() + "-" + RandomUtil.randomAlphanumeric(32);
	}

	//=====================================私有方法  end=====================================

}
