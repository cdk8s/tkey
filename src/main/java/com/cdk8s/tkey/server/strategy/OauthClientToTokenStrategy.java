package com.cdk8s.tkey.server.strategy;


import com.cdk8s.tkey.server.constant.GlobalVariable;
import com.cdk8s.tkey.server.pojo.bo.handle.OauthTokenStrategyHandleBO;
import com.cdk8s.tkey.server.pojo.dto.OauthToken;
import com.cdk8s.tkey.server.pojo.dto.param.OauthTokenParam;
import com.cdk8s.tkey.server.service.OauthCheckParamService;
import com.cdk8s.tkey.server.service.OauthGenerateService;
import com.cdk8s.tkey.server.service.OauthSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service(GlobalVariable.OAUTH_CLIENT_GRANT_TYPE)
public class OauthClientToTokenStrategy implements OauthTokenStrategyInterface {

	@Autowired
	private OauthCheckParamService oauthCheckParamService;

	@Autowired
	private OauthGenerateService oauthGenerateService;

	@Autowired
	private OauthSaveService oauthSaveService;

	//=====================================业务处理 start=====================================

	@Override
	public void checkParam(OauthTokenParam oauthTokenParam, OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO) {
		oauthCheckParamService.checkClientIdAndClientSecretParam(oauthTokenParam.getClientId(), oauthTokenParam.getClientSecret());
	}

	@Override
	public OauthToken handle(OauthTokenParam oauthTokenParam, OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO) {
		OauthToken oauthTokenInfoByClientBO = oauthGenerateService.generateOauthTokenInfoBO(true);

		oauthSaveService.saveAccessToken(oauthTokenInfoByClientBO.getAccessToken(), null, oauthTokenParam.getClientId(), GlobalVariable.OAUTH_CLIENT_GRANT_TYPE);
		oauthSaveService.saveRefreshToken(oauthTokenInfoByClientBO.getRefreshToken(), null, oauthTokenParam.getClientId(), GlobalVariable.OAUTH_CLIENT_GRANT_TYPE);

		return oauthTokenInfoByClientBO;
	}

	//=====================================业务处理 end=====================================

}
