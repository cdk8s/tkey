package com.cdk8s.tkey.server.strategy;


import com.cdk8s.tkey.server.constant.GlobalVariable;
import com.cdk8s.tkey.server.exception.OauthApiException;
import com.cdk8s.tkey.server.pojo.bo.cache.OauthCodeToRedisBO;
import com.cdk8s.tkey.server.pojo.bo.cache.OauthUserInfoToRedisBO;
import com.cdk8s.tkey.server.pojo.bo.handle.OauthTokenStrategyHandleBO;
import com.cdk8s.tkey.server.pojo.dto.OauthToken;
import com.cdk8s.tkey.server.pojo.dto.OauthUserAttribute;
import com.cdk8s.tkey.server.pojo.dto.param.OauthTokenParam;
import com.cdk8s.tkey.server.service.OauthCheckParamService;
import com.cdk8s.tkey.server.service.OauthGenerateService;
import com.cdk8s.tkey.server.service.OauthSaveService;
import com.cdk8s.tkey.server.util.StringUtil;
import com.cdk8s.tkey.server.util.redis.StringRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service(GlobalVariable.OAUTH_CODE_GRANT_TYPE)
public class OauthCodeToTokenStrategy implements OauthTokenStrategyInterface {

	@Autowired
	private StringRedisService<String, OauthCodeToRedisBO> codeRedisService;

	@Autowired
	private StringRedisService<String, OauthUserInfoToRedisBO> userInfoRedisService;

	@Autowired
	private OauthCheckParamService oauthCheckParamService;

	@Autowired
	private OauthGenerateService oauthGenerateService;

	@Autowired
	private OauthSaveService oauthSaveService;

	//=====================================业务处理 start=====================================

	@Override
	public void checkParam(OauthTokenParam oauthTokenParam, OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO) {
		oauthCheckParamService.checkOauthTokenParam(oauthTokenParam);

		OauthCodeToRedisBO oauthCodeToRedisBO = codeRedisService.get(oauthTokenParam.getCode());
		if (null == oauthCodeToRedisBO) {
			throw new OauthApiException("code 无效");
		}

		if (StringUtil.notEqualsIgnoreCase(oauthCodeToRedisBO.getClientId(), oauthTokenParam.getClientId())) {
			throw new OauthApiException("该 code 与当前请求的 client_id 参数不匹配");
		}

		oauthTokenStrategyHandleBO.setUserInfoRedisKey(oauthCodeToRedisBO.getUserInfoRedisKey());
	}

	@Override
	public OauthToken handle(OauthTokenParam oauthTokenParam, OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO) {
		String userInfoRedisKey = oauthTokenStrategyHandleBO.getUserInfoRedisKey();
		OauthUserInfoToRedisBO oauthUserInfoToRedisBO = userInfoRedisService.get(userInfoRedisKey);

		OauthToken oauthToken = oauthGenerateService.generateOauthTokenInfoBO(true);

		OauthUserAttribute userAttribute = oauthUserInfoToRedisBO.getUserAttribute();
		oauthSaveService.saveAccessToken(oauthToken.getAccessToken(), userAttribute, oauthTokenParam.getClientId(), GlobalVariable.OAUTH_CODE_GRANT_TYPE);
		oauthSaveService.saveRefreshToken(oauthToken.getRefreshToken(), userAttribute, oauthTokenParam.getClientId(), GlobalVariable.OAUTH_CODE_GRANT_TYPE);

		// code 只能被用一次，这里用完会立马被删除
		codeRedisService.delete(oauthTokenParam.getCode());
		return oauthToken;
	}

	//=====================================业务处理 end=====================================

}
