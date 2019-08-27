package com.cdk8s.tkey.server.strategy;


import com.cdk8s.tkey.server.constant.GlobalVariable;
import com.cdk8s.tkey.server.exception.OauthApiException;
import com.cdk8s.tkey.server.pojo.bo.handle.OauthTokenStrategyHandleBO;
import com.cdk8s.tkey.server.pojo.dto.OauthToken;
import com.cdk8s.tkey.server.pojo.dto.OauthUserAttribute;
import com.cdk8s.tkey.server.pojo.dto.param.OauthTokenParam;
import com.cdk8s.tkey.server.retry.RetryService;
import com.cdk8s.tkey.server.service.OauthCheckParamService;
import com.cdk8s.tkey.server.service.OauthGenerateService;
import com.cdk8s.tkey.server.service.OauthSaveService;
import com.cdk8s.tkey.server.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service(GlobalVariable.OAUTH_PASSWORD_GRANT_TYPE)
public class OauthPasswordToTokenStrategy implements OauthTokenStrategyInterface {

	@Autowired
	private OauthCheckParamService oauthCheckParamService;

	@Autowired
	private OauthGenerateService oauthGenerateService;

	@Autowired
	private OauthSaveService oauthSaveService;

	@Autowired
	private RetryService retryService;

	//=====================================业务处理 start=====================================

	@Override
	public void checkParam(OauthTokenParam oauthTokenParam, OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO) {
		oauthCheckParamService.checkClientIdParam(oauthTokenParam.getClientId());
	}

	@Override
	public OauthToken handle(OauthTokenParam oauthTokenParam, OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO) {
		String clientId = oauthTokenParam.getClientId();
		String clientSecret = oauthTokenParam.getClientSecret();
		oauthCheckParamService.checkClientIdAndClientSecretParam(clientId, clientSecret);
		String username = oauthTokenParam.getUsername();
		String password = oauthTokenParam.getPassword();
		oauthCheckParamService.checkUsernamePasswordParam(username, password);

		// 校验用户名密码
		OauthUserAttribute oauthUserAttribute = retryService.getOauthUserAttributeBO(username, password);
		if (null == oauthUserAttribute || StringUtil.isBlank(oauthUserAttribute.getUserId())) {
			throw new OauthApiException("用户名或密码错误");
		}

		String userInfoRedisKey = oauthGenerateService.generateUserInfoRedisKey(oauthUserAttribute.getUserId());
		oauthSaveService.saveUserInfoKeyToRedis(userInfoRedisKey, oauthUserAttribute);

		oauthTokenStrategyHandleBO.setUserAttribute(oauthUserAttribute);

		OauthUserAttribute userAttribute = oauthTokenStrategyHandleBO.getUserAttribute();

		OauthToken oauthTokenInfoByClientBO = oauthGenerateService.generateOauthTokenInfoBO(true);

		oauthSaveService.saveAccessToken(oauthTokenInfoByClientBO.getAccessToken(), userAttribute, clientId, GlobalVariable.OAUTH_PASSWORD_GRANT_TYPE);
		oauthSaveService.saveRefreshToken(oauthTokenInfoByClientBO.getRefreshToken(), userAttribute, clientId, GlobalVariable.OAUTH_PASSWORD_GRANT_TYPE);

		return oauthTokenInfoByClientBO;
	}

	//=====================================业务处理 end=====================================

}
