package com.cdk8s.tkey.server.init;

import com.cdk8s.tkey.server.constant.GlobalVariable;
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
	private StringRedisService<String, String> stringRedisService;

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
		stringRedisService.set(GlobalVariable.REDIS_CLIENT_ID_PREFIX + oauthClientToRedisBO.getClientId(), JsonUtil.toJson(oauthClientToRedisBO));

		accessTokenRedisService.set("AT-102-uUCkO2NgITHWJSD16g89C9loMwCVSQqh", getAccessToken(), oauthProperties.getAccessTokenMaxTimeToLiveInSeconds());
		refreshTokenRedisService.set("RT-103-zIYUBA0ddql5cyYGEdpmPcRJH63hOVpQ", getRefreshToken(), oauthProperties.getRefreshTokenMaxTimeToLiveInSeconds());
		codeRedisService.set("OC-106-uUddPxoWCEa4NBO5GaVIRJOTZLlWbHNr", getCode(), oauthProperties.getCodeMaxTimeToLiveInSeconds());
		codeRedisService.set("OC-107-uUddPxoWCEa4NBO5GaVIRJOTZLlWbHNr", getCode(), oauthProperties.getCodeMaxTimeToLiveInSeconds());

		log.info("=================================预设 Redis 测试数据 End=================================");

	}

	//=====================================业务处理  end=====================================
	//=====================================私有方法 start=====================================

	private OauthClientToRedisBO getClient() {
		OauthClientToRedisBO oauthClientToRedisBO = new OauthClientToRedisBO();
		oauthClientToRedisBO.setId(111111L);
		oauthClientToRedisBO.setClientName("通用测试系统1");
		oauthClientToRedisBO.setClientId("test_client_id_1");
		oauthClientToRedisBO.setClientSecret("test_client_secret_1");
		oauthClientToRedisBO.setClientUrl("^(http|https)://.*");
		oauthClientToRedisBO.setClientDesc("通用测试系统1");
		oauthClientToRedisBO.setLogoUrl("https://www.easyicon.net/api/resizeApi.php?id=1200686&size=32");
		return oauthClientToRedisBO;
	}

	private OauthAccessTokenToRedisBO getAccessToken() {
		OauthAccessTokenToRedisBO oauthAccessTokenToRedisBO = new OauthAccessTokenToRedisBO();
		OauthUserAttribute oauthUserAttribute = new OauthUserAttribute();
		oauthUserAttribute.setEmail("admin@cdk8s.com");
		oauthUserAttribute.setUserId("111222333");
		oauthUserAttribute.setUsername("admin");

		oauthAccessTokenToRedisBO.setUserAttribute(oauthUserAttribute);
		oauthAccessTokenToRedisBO.setGrantType("authorization_code");
		oauthAccessTokenToRedisBO.setClientId("test_client_id_1");
		oauthAccessTokenToRedisBO.setIat(1561522123L);
		return oauthAccessTokenToRedisBO;
	}

	private OauthRefreshTokenToRedisBO getRefreshToken() {
		OauthRefreshTokenToRedisBO oauthRefreshTokenToRedisBO = new OauthRefreshTokenToRedisBO();
		oauthRefreshTokenToRedisBO.setUserInfoRedisKey("USER-111222333");
		oauthRefreshTokenToRedisBO.setGrantType("authorization_code");
		oauthRefreshTokenToRedisBO.setClientId("test_client_id_1");
		oauthRefreshTokenToRedisBO.setIat(1561522123L);
		return oauthRefreshTokenToRedisBO;

	}

	private OauthCodeToRedisBO getCode() {
		OauthCodeToRedisBO oauthCodeToRedisBO = new OauthCodeToRedisBO();
		oauthCodeToRedisBO.setTgc("TGC-101-1uo81h7S5ho3ItlGA4cYdF4YIfpOkJDJ");
		oauthCodeToRedisBO.setUserInfoRedisKey("USER-111222333");
		oauthCodeToRedisBO.setClientId("test_client_id_1");
		oauthCodeToRedisBO.setIat(1561522123L);
		return oauthCodeToRedisBO;
	}

	//=====================================私有方法  end=====================================
}
