package com.cdk8s.tkey.server.init;

import com.cdk8s.tkey.server.constant.GlobalVariable;
import com.cdk8s.tkey.server.constant.GlobalVariableToJunit;
import com.cdk8s.tkey.server.pojo.bo.cache.OauthAccessTokenToRedisBO;
import com.cdk8s.tkey.server.pojo.bo.cache.OauthClientToRedisBO;
import com.cdk8s.tkey.server.pojo.bo.cache.OauthCodeToRedisBO;
import com.cdk8s.tkey.server.pojo.bo.cache.OauthRefreshTokenToRedisBO;
import com.cdk8s.tkey.server.pojo.dto.OauthUserAttribute;
import com.cdk8s.tkey.server.properties.OauthProperties;
import com.cdk8s.tkey.server.util.JsonUtil;
import com.cdk8s.tkey.server.util.redis.StringRedisService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Slf4j
@Profile({"dev", "gatling", "test", "junit"})
@Component
public class ApplicationTestDataInitRunner implements ApplicationRunner {

	@Autowired
	private StringRedisService<String, String> clientRedisService;

	@Autowired
	private StringRedisService<String, OauthCodeToRedisBO> codeRedisService;

	@Autowired
	private StringRedisService<String, OauthAccessTokenToRedisBO> accessTokenRedisService;

	@Autowired
	private StringRedisService<String, OauthRefreshTokenToRedisBO> refreshTokenRedisService;

	@Autowired
	private OauthProperties oauthProperties;

	//=====================================业务处理 start=====================================

	@SneakyThrows
	@Override
	public void run(ApplicationArguments args) {
		log.info("=================================预设 Redis 测试数据 Start=================================");

		OauthClientToRedisBO oauthClientToRedisBO = getClient();
		clientRedisService.set(GlobalVariable.REDIS_CLIENT_ID_KEY_PREFIX + oauthClientToRedisBO.getClientId(), JsonUtil.toJson(oauthClientToRedisBO));

		accessTokenRedisService.set(GlobalVariable.REDIS_OAUTH_ACCESS_TOKEN_KEY_PREFIX + GlobalVariableToJunit.ACCESS_TOKEN, getAccessToken(), oauthProperties.getAccessTokenMaxTimeToLiveInSeconds());
		refreshTokenRedisService.set(GlobalVariable.REDIS_OAUTH_REFRESH_TOKEN_KEY_PREFIX + GlobalVariableToJunit.REFRESH_TOKEN, getRefreshToken(), oauthProperties.getRefreshTokenMaxTimeToLiveInSeconds());
		codeRedisService.set(GlobalVariable.REDIS_OAUTH_CODE_PREFIX_KEY_PREFIX + GlobalVariableToJunit.CODE, getCode(), oauthProperties.getCodeMaxTimeToLiveInSeconds());
		codeRedisService.set(GlobalVariable.REDIS_OAUTH_CODE_PREFIX_KEY_PREFIX + GlobalVariableToJunit.CODE2, getCode(), oauthProperties.getCodeMaxTimeToLiveInSeconds());

		log.info("=================================预设 Redis 测试数据 End=================================");

	}

	//=====================================业务处理  end=====================================
	//=====================================私有方法 start=====================================

	private OauthClientToRedisBO getClient() {
		OauthClientToRedisBO oauthClientToRedisBO = new OauthClientToRedisBO();
		oauthClientToRedisBO.setId(GlobalVariableToJunit.ID_LONG);
		oauthClientToRedisBO.setClientName("通用测试系统1");
		oauthClientToRedisBO.setClientId(GlobalVariableToJunit.CLIENT_ID);
		oauthClientToRedisBO.setClientSecret("test_client_secret_1");
		oauthClientToRedisBO.setClientUrl("^(http|https)://.*");
		oauthClientToRedisBO.setClientDesc("通用测试系统1");
		oauthClientToRedisBO.setLogoUrl("https://www.easyicon.net/api/resizeApi.php?id=1200686&size=32");
		return oauthClientToRedisBO;
	}

	private OauthAccessTokenToRedisBO getAccessToken() {
		OauthAccessTokenToRedisBO oauthAccessTokenToRedisBO = new OauthAccessTokenToRedisBO();
		OauthUserAttribute oauthUserAttribute = new OauthUserAttribute();
		oauthUserAttribute.setEmail(GlobalVariableToJunit.USER_EMAIL);
		oauthUserAttribute.setUserId(GlobalVariableToJunit.USER_ID);
		oauthUserAttribute.setUsername(GlobalVariableToJunit.USERNAME);

		oauthAccessTokenToRedisBO.setUserAttribute(oauthUserAttribute);
		oauthAccessTokenToRedisBO.setGrantType(GlobalVariableToJunit.CODE_GRANT_TYPE);
		oauthAccessTokenToRedisBO.setClientId(GlobalVariableToJunit.CLIENT_ID);
		oauthAccessTokenToRedisBO.setIat(1561522123L);
		return oauthAccessTokenToRedisBO;
	}

	private OauthRefreshTokenToRedisBO getRefreshToken() {
		OauthRefreshTokenToRedisBO oauthRefreshTokenToRedisBO = new OauthRefreshTokenToRedisBO();
		oauthRefreshTokenToRedisBO.setUserInfoRedisKey(GlobalVariableToJunit.USER_INFO_REDIS_KEY);
		oauthRefreshTokenToRedisBO.setGrantType(GlobalVariableToJunit.CODE_GRANT_TYPE);
		oauthRefreshTokenToRedisBO.setClientId(GlobalVariableToJunit.CLIENT_ID);
		oauthRefreshTokenToRedisBO.setIat(1561522123L);
		return oauthRefreshTokenToRedisBO;

	}

	private OauthCodeToRedisBO getCode() {
		OauthCodeToRedisBO oauthCodeToRedisBO = new OauthCodeToRedisBO();
		oauthCodeToRedisBO.setTgc(GlobalVariableToJunit.TGC);
		oauthCodeToRedisBO.setUserInfoRedisKey(GlobalVariableToJunit.USER_INFO_REDIS_KEY);
		oauthCodeToRedisBO.setClientId(GlobalVariableToJunit.CLIENT_ID);
		oauthCodeToRedisBO.setIat(1561522123L);
		return oauthCodeToRedisBO;
	}

	//=====================================私有方法  end=====================================
}
