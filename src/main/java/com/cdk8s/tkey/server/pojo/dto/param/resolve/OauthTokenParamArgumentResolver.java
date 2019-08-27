package com.cdk8s.tkey.server.pojo.dto.param.resolve;

import com.cdk8s.tkey.server.pojo.dto.param.OauthTokenParam;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class OauthTokenParamArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.getParameterType().equals(OauthTokenParam.class);
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
		OauthTokenParam param = new OauthTokenParam();
		param.setGrantType(nativeWebRequest.getParameter("grant_type"));
		param.setClientId(nativeWebRequest.getParameter("client_id"));
		param.setClientSecret(nativeWebRequest.getParameter("client_secret"));
		param.setCode(nativeWebRequest.getParameter("code"));
		param.setRefreshToken(nativeWebRequest.getParameter("refresh_token"));
		param.setRedirectUri(nativeWebRequest.getParameter("redirect_uri"));
		param.setUsername(nativeWebRequest.getParameter("username"));
		param.setPassword(nativeWebRequest.getParameter("password"));
		return param;
	}

}
