package com.cdk8s.tkey.server.controller;


import com.cdk8s.tkey.server.constant.GlobalVariable;
import com.cdk8s.tkey.server.enums.ResponseProduceTypeEnum;
import com.cdk8s.tkey.server.exception.OauthApiException;
import com.cdk8s.tkey.server.pojo.bo.cache.*;
import com.cdk8s.tkey.server.pojo.bo.handle.OauthTokenStrategyHandleBO;
import com.cdk8s.tkey.server.pojo.dto.OauthIntrospect;
import com.cdk8s.tkey.server.pojo.dto.OauthToken;
import com.cdk8s.tkey.server.pojo.dto.OauthUserAttribute;
import com.cdk8s.tkey.server.pojo.dto.OauthUserProfile;
import com.cdk8s.tkey.server.pojo.dto.param.*;
import com.cdk8s.tkey.server.properties.OauthProperties;
import com.cdk8s.tkey.server.retry.RetryService;
import com.cdk8s.tkey.server.service.OauthCheckParamService;
import com.cdk8s.tkey.server.service.OauthGenerateService;
import com.cdk8s.tkey.server.service.OauthSaveService;
import com.cdk8s.tkey.server.strategy.OauthTokenStrategyContext;
import com.cdk8s.tkey.server.util.CodecUtil;
import com.cdk8s.tkey.server.util.CookieUtil;
import com.cdk8s.tkey.server.util.IPUtil;
import com.cdk8s.tkey.server.util.StringUtil;
import com.cdk8s.tkey.server.util.redis.StringRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("/oauth")
public class OauthController {

	@Autowired
	private StringRedisService<String, OauthTgcToRedisBO> tgcRedisService;

	@Autowired
	private StringRedisService<String, OauthUserInfoToRedisBO> userInfoRedisService;

	@Autowired
	private StringRedisService<String, OauthAccessTokenToRedisBO> accessTokenRedisService;

	@Autowired
	private StringRedisService<String, OauthRefreshTokenToRedisBO> refreshTokenRedisService;

	@Autowired
	private OauthCheckParamService oauthCheckParamService;

	@Autowired
	private OauthGenerateService oauthGenerateService;

	@Autowired
	private OauthSaveService oauthSaveService;

	@Autowired
	private RetryService retryService;

	@Autowired
	private OauthTokenStrategyContext oauthTokenStrategyContext;

	@Autowired
	private OauthProperties oauthProperties;

	//=====================================业务处理 start=====================================

	/**
	 * 登录页面入口
	 */
	@RequestMapping(value = "/authorize", method = RequestMethod.GET)
	public String authorize(final HttpServletRequest request, ModelMap model, OauthAuthorizeParam oAuthAuthorizeParam) {

		OauthClientToRedisBO oauthClientToRedisBO = oauthCheckParamService.checkOauthAuthorizeParam(oAuthAuthorizeParam);

		model.put(GlobalVariable.DEFAULT_LOGIN_PAGE_CLIENT_INFO_KEY, oauthClientToRedisBO);

		String tgcCookieValue = CookieUtil.getCookie(request, GlobalVariable.OAUTH_SERVER_COOKIE_KEY);
		if (StringUtil.isBlank(tgcCookieValue)) {
			return GlobalVariable.DEFAULT_LOGIN_PAGE_PATH;
		}

		String userInfoRedisKey = oauthCheckParamService.checkCookieTgc(request.getHeader(GlobalVariable.HTTP_HEADER_USER_AGENT), IPUtil.getIp(request), tgcCookieValue);

		String finalRedirectUrl;
		String redirectUri = oAuthAuthorizeParam.getRedirectUri();
		if (StringUtil.equalsIgnoreCase(oAuthAuthorizeParam.getResponseType(), GlobalVariable.OAUTH_TOKEN_RESPONSE_TYPE)) {
			// 简化模式
			OauthUserInfoToRedisBO oauthUserInfoToRedisBO = userInfoRedisService.get(userInfoRedisKey);

			OauthToken oauthTokenInfoByCodePO = oauthGenerateService.generateOauthTokenInfoBO(true);
			oauthSaveService.saveAccessToken(oauthTokenInfoByCodePO.getAccessToken(), oauthUserInfoToRedisBO.getUserAttribute(), oAuthAuthorizeParam.getClientId(), GlobalVariable.OAUTH_TOKEN_GRANT_TYPE);
			oauthSaveService.saveRefreshToken(oauthTokenInfoByCodePO.getRefreshToken(), oauthUserInfoToRedisBO.getUserAttribute(), oAuthAuthorizeParam.getClientId(), GlobalVariable.OAUTH_TOKEN_GRANT_TYPE);
			finalRedirectUrl = getRedirectUrlWithAccessToken(redirectUri, oauthTokenInfoByCodePO);
		} else {
			// 授权码模式
			String code = oauthGenerateService.generateCode();
			oauthSaveService.saveCodeToRedis(code, tgcCookieValue, userInfoRedisKey, oAuthAuthorizeParam.getClientId());
			finalRedirectUrl = getRedirectUrlWithCode(redirectUri, oAuthAuthorizeParam.getState(), code);
		}

		oauthSaveService.updateTgcAndUserInfoRedisKeyExpire(tgcCookieValue, userInfoRedisKey);
		return GlobalVariable.REDIRECT_URI_PREFIX + finalRedirectUrl;
	}

	/**
	 * 表单登录接口：验证用户名和密码
	 */
	@RequestMapping(value = "/authorize", method = RequestMethod.POST)
	public String formLogin(final HttpServletRequest request, final HttpServletResponse response, ModelMap model, OauthFormLoginParam oauthFormLoginParam) {

		OauthClientToRedisBO oauthClientToRedisBO;
		OauthUserAttribute oauthUserAttribute;
		String userAgent = request.getHeader(GlobalVariable.HTTP_HEADER_USER_AGENT);
		String requestIp = IPUtil.getIp(request);

		try {
			oauthClientToRedisBO = oauthCheckParamService.checkClientIdParam(oauthFormLoginParam.getClientId());
			oauthCheckParamService.checkUserAgentAndRequestIpParam(userAgent, requestIp);
			oauthCheckParamService.checkOauthFormLoginParam(oauthFormLoginParam);

			model.put(GlobalVariable.DEFAULT_LOGIN_PAGE_CLIENT_INFO_KEY, oauthClientToRedisBO);

			// 校验用户名密码
			oauthUserAttribute = requestLoginApi(oauthFormLoginParam);
		} catch (Exception e) {
			model.put(GlobalVariable.DEFAULT_LOGIN_ERROR_KEY, e.getMessage());
			return GlobalVariable.DEFAULT_LOGIN_PAGE_PATH;
		}

		String userInfoRedisKey = oauthGenerateService.generateUserInfoRedisKey(oauthUserAttribute.getUserId());
		oauthSaveService.saveUserInfoKeyToRedis(userInfoRedisKey, oauthUserAttribute);

		boolean isRememberMe = oauthFormLoginParam.getBoolIsRememberMe();
		String tgc = oauthGenerateService.generateTgc();

		Integer maxTimeToLiveInSeconds = oauthProperties.getTgcAndUserInfoMaxTimeToLiveInSeconds();
		if (isRememberMe) {
			maxTimeToLiveInSeconds = oauthProperties.getRememberMeMaxTimeToLiveInSeconds();
		}
		CookieUtil.setCookie(response, GlobalVariable.OAUTH_SERVER_COOKIE_KEY, tgc, maxTimeToLiveInSeconds, true, oauthProperties.getTgcCookieSecure());

		oauthSaveService.saveTgcToRedisAndCookie(tgc, maxTimeToLiveInSeconds, userInfoRedisKey, userAgent, requestIp, isRememberMe);

		String finalRedirectUrl;
		String redirectUri = oauthFormLoginParam.getRedirectUri();
		if (StringUtil.equalsIgnoreCase(oauthFormLoginParam.getResponseType(), GlobalVariable.OAUTH_TOKEN_RESPONSE_TYPE)) {
			// 简化模式
			OauthToken oauthToken = oauthGenerateService.generateOauthTokenInfoBO(true);
			oauthSaveService.saveAccessToken(oauthToken.getAccessToken(), oauthUserAttribute, oauthFormLoginParam.getClientId(), GlobalVariable.OAUTH_TOKEN_GRANT_TYPE);
			oauthSaveService.saveRefreshToken(oauthToken.getRefreshToken(), oauthUserAttribute, oauthFormLoginParam.getClientId(), GlobalVariable.OAUTH_TOKEN_GRANT_TYPE);
			finalRedirectUrl = getRedirectUrlWithAccessToken(redirectUri, oauthToken);
		} else {
			// 授权码模式
			String code = oauthGenerateService.generateCode();
			oauthSaveService.saveCodeToRedis(code, tgc, userInfoRedisKey, oauthFormLoginParam.getClientId());
			finalRedirectUrl = getRedirectUrlWithCode(redirectUri, oauthFormLoginParam.getState(), code);
		}

		return GlobalVariable.REDIRECT_URI_PREFIX + finalRedirectUrl;

	}


	/**
	 * 换取 token（授权码模式、客户端模式、密码模式、刷新模式）
	 */
	@RequestMapping(value = "/token", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<?> token(final HttpServletRequest request, OauthTokenParam oauthTokenParam) {
		String grantType = oauthTokenParam.getGrantType();
		oauthCheckParamService.checkGrantTypeParam(grantType);
		resolveRequestHeader(request, oauthTokenParam);

		OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO = new OauthTokenStrategyHandleBO();
		oauthTokenStrategyContext.checkParam(grantType, oauthTokenParam, oauthTokenStrategyHandleBO);

		OauthToken oauthToken = oauthTokenStrategyContext.generateOauthTokenInfo(grantType, oauthTokenParam, oauthTokenStrategyHandleBO);
		return ResponseEntity.ok(oauthToken);
	}


	/**
	 * 根据 AccessToken 获取用户信息
	 */
	@RequestMapping(value = "/userinfo", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<?> userinfo(final HttpServletRequest request) {
		OauthAccessTokenToRedisBO oauthAccessTokenToRedisBO = oauthCheckParamService.checkAccessTokenParam(request);

		OauthUserProfile oauthUserProfile = new OauthUserProfile();
		buildOauthUserInfoByTokenDTO(oauthUserProfile, oauthAccessTokenToRedisBO);

		return ResponseEntity.ok(oauthUserProfile);
	}


	/**
	 * 验证 AccessToken / RefreshToken 有效性和基础信息
	 */
	@RequestMapping(value = "/introspect", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> introspect(final HttpServletRequest request, OauthIntrospectTokenParam oauthIntrospectTokenParam) {

		resolveRequestHeader(request, oauthIntrospectTokenParam);
		OauthIntrospect oauthIntrospect = oauthCheckParamService.checkOauthIntrospectTokenParam(oauthIntrospectTokenParam);

		Long iat = 0L;
		String grantType = "";

		String token = oauthIntrospectTokenParam.getToken();
		String tokenTypeHint = oauthIntrospectTokenParam.getTokenTypeHint();
		if (StringUtil.equalsIgnoreCase(tokenTypeHint, GlobalVariable.OAUTH_ACCESS_TOKEN_TYPE_HINT)) {
			// 验证 AccessToken
			OauthAccessTokenToRedisBO oauthTokenToRedisDTO = accessTokenRedisService.get(token);
			if (null == oauthTokenToRedisDTO) {
				throw new OauthApiException("token 已失效");
			}
			grantType = oauthTokenToRedisDTO.getGrantType();
			iat = oauthTokenToRedisDTO.getIat();
			oauthIntrospect.setExp(iat + oauthProperties.getAccessTokenMaxTimeToLiveInSeconds());
		} else if (StringUtil.equalsIgnoreCase(tokenTypeHint, GlobalVariable.OAUTH_REFRESH_TOKEN_GRANT_TYPE)) {
			// 验证 RefreshToken
			OauthRefreshTokenToRedisBO oauthTokenToRedisDTO = refreshTokenRedisService.get(token);
			if (null == oauthTokenToRedisDTO) {
				throw new OauthApiException("token 已失效");
			}
			grantType = oauthTokenToRedisDTO.getGrantType();
			iat = oauthTokenToRedisDTO.getIat();
			oauthIntrospect.setExp(iat + oauthProperties.getRefreshTokenMaxTimeToLiveInSeconds());
		}

		oauthIntrospect.setGrantType(grantType);
		oauthIntrospect.setIat(iat);

		return ResponseEntity.ok(oauthIntrospect);
	}

	/**
	 * 登出
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(final HttpServletRequest request, final HttpServletResponse response, @RequestParam(value = "redirect_uri", required = false) String redirectUri) {

		String tgcCookieValue = CookieUtil.getCookie(request, GlobalVariable.OAUTH_SERVER_COOKIE_KEY);
		if (StringUtil.isNotBlank(tgcCookieValue)) {
			tgcRedisService.delete(tgcCookieValue);
			CookieUtil.deleteCookie(request, response, GlobalVariable.OAUTH_SERVER_COOKIE_KEY);
		}

		if (StringUtil.isNotBlank(redirectUri)) {
			return GlobalVariable.REDIRECT_URI_PREFIX + redirectUri;
		}

		return GlobalVariable.DEFAULT_LOGOUT_PAGE_PATH;

	}

	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================


	private OauthUserAttribute requestLoginApi(OauthFormLoginParam oauthFormLoginParam) {
		// 为了防止 UPMS 接口抖动，这里做了 retry 机制
		OauthUserAttribute oauthUserAttribute = retryService.getOauthUserAttributeBO(oauthFormLoginParam.getUsername(), oauthFormLoginParam.getPassword());
		if (null == oauthUserAttribute || StringUtil.isBlank(oauthUserAttribute.getUserId())) {
			log.error("调用 UPMS 接口返回错误信息，用户名：<{}>", oauthFormLoginParam.getUsername());
			throw new OauthApiException("演示模式下，用户名：admin，密码：123456", ResponseProduceTypeEnum.HTML, GlobalVariable.DEFAULT_LOGIN_PAGE_PATH);
		}
		return oauthUserAttribute;
	}

	private void buildOauthUserInfoByTokenDTO(OauthUserProfile oauthUserProfile, OauthAccessTokenToRedisBO oauthAccessTokenToRedisBO) {
		OauthUserAttribute oauthUserAttribute = oauthAccessTokenToRedisBO.getUserAttribute();

		if (null != oauthUserAttribute) {
			oauthUserProfile.setUserAttribute(oauthUserAttribute);
			oauthUserProfile.setUsername(oauthUserAttribute.getUsername());
			oauthUserProfile.setName(oauthUserAttribute.getUsername());
			oauthUserProfile.setUserId(oauthUserAttribute.getUserId());
			oauthUserProfile.setId(oauthUserAttribute.getUserId());
		} else {
			// 客户端模式情况下是没有用户信息的
			oauthUserProfile.setUserAttribute(new OauthUserAttribute());
		}

		oauthUserProfile.setIat(oauthAccessTokenToRedisBO.getIat());
		oauthUserProfile.setExp(oauthAccessTokenToRedisBO.getIat() + oauthProperties.getAccessTokenMaxTimeToLiveInSeconds());
		oauthUserProfile.setClientId(oauthAccessTokenToRedisBO.getClientId());
		oauthUserProfile.setGrantType(oauthAccessTokenToRedisBO.getGrantType());

	}

	private String getRedirectUrlWithCode(String redirectUri, String state, String code) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(redirectUri);
		if (StringUtil.containsIgnoreCase(redirectUri, "?")) {
			stringBuilder.append("&");
		} else {
			stringBuilder.append("?");
		}
		stringBuilder.append(GlobalVariable.OAUTH_CODE_RESPONSE_TYPE);
		stringBuilder.append("=");
		stringBuilder.append(code);
		if (StringUtil.isNotBlank(state)) {
			stringBuilder.append("&");
			stringBuilder.append(GlobalVariable.OAUTH_STATE_KEY);
			stringBuilder.append("=");
			stringBuilder.append(state);
		}

		return stringBuilder.toString();
	}

	private String getRedirectUrlWithAccessToken(String redirectUri, OauthToken oauthToken) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(redirectUri);
		stringBuilder.append("#");
		stringBuilder.append(GlobalVariable.OAUTH_ACCESS_TOKEN_KEY);
		stringBuilder.append("=");
		stringBuilder.append(oauthToken.getAccessToken());
		stringBuilder.append("&");
		stringBuilder.append(GlobalVariable.OAUTH_TOKEN_TYPE_KEY);
		stringBuilder.append("=");
		stringBuilder.append(GlobalVariable.OAUTH_TOKEN_TYPE);
		stringBuilder.append("&");
		stringBuilder.append(GlobalVariable.OAUTH_EXPIRES_IN_KEY);
		stringBuilder.append("=");
		stringBuilder.append(oauthProperties.getAccessTokenMaxTimeToLiveInSeconds());
		stringBuilder.append("&");
		stringBuilder.append(GlobalVariable.OAUTH_REFRESH_TOKEN_KEY);
		stringBuilder.append("=");
		stringBuilder.append(oauthToken.getRefreshToken());
		return stringBuilder.toString();
	}

	private void resolveRequestHeader(HttpServletRequest request, OauthClientParam oauthClientParam) {
		String authorizationHeader = request.getHeader(GlobalVariable.HTTP_HEADER_AUTHORIZATION);
		if (StringUtil.isBlank(authorizationHeader)) {
			return;
		}

		if (StringUtil.containsIgnoreCase(authorizationHeader, GlobalVariable.BASIC_AUTH_UPPER_PREFIX)) {
			String replaceIgnoreCase = StringUtil.replaceIgnoreCase(authorizationHeader, GlobalVariable.BASIC_AUTH_UPPER_PREFIX, GlobalVariable.BASIC_AUTH_LOWER_PREFIX);
			authorizationHeader = StringUtil.substringAfter(replaceIgnoreCase, GlobalVariable.BASIC_AUTH_LOWER_PREFIX);
		}
		String basic = CodecUtil.base64DecodeBySafe(authorizationHeader);
		List<String> stringList = StringUtil.split(basic, ":");
		if (stringList.size() < 2) {
			return;
		}
		oauthClientParam.setClientId(stringList.get(0));
		oauthClientParam.setClientSecret(stringList.get(1));

	}
	//=====================================私有方法  end=====================================

}
