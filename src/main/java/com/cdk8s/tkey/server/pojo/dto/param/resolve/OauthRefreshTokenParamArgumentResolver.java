package com.cdk8s.tkey.server.pojo.dto.param.resolve;

import com.cdk8s.tkey.server.pojo.dto.param.OauthRefreshTokenParam;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class OauthRefreshTokenParamArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.getParameterType().equals(OauthRefreshTokenParam.class);
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
		OauthRefreshTokenParam param = new OauthRefreshTokenParam();
		param.setGrantType(nativeWebRequest.getParameter("grant_type"));
		param.setRefreshToken(nativeWebRequest.getParameter("refresh_token"));
		param.setClientId(nativeWebRequest.getParameter("client_id"));
		param.setClientSecret(nativeWebRequest.getParameter("client_secret"));
		return param;
	}

}
